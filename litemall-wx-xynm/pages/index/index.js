const util = require('../../utils/util.js');
const api = require('../../config/api.js');
const user = require('../../utils/user.js');

//获取应用实例
const app = getApp();

Page({
  data: {
    banner: [],
    channel: [],
    lists: [],
    userId:0,
    userLogin: false
  },

  onShareAppMessage: function() {
    return {
      title: '鲜衣怒马',
      desc: '鲜衣怒马少年时,一日看尽长安花',
      path: '/pages/index/index'
    }
  },

  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getIndexData();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },

  onShow: function () {
    // Need call api fetch data to refresh
    if (app.globalData.userLogin != this.data.userLogin) {
      this.getIndexData();
    }
    // Do need call api
    else {
      let currentRecord = app.globalData.currentRecord;
      let listsOneMore = this.data.lists;
      listsOneMore.unshift(currentRecord);
      if (currentRecord) {
        this.setData({
          lists: listsOneMore
        })
      }
    }
    app.globalData.currentRecord = undefined;
  },

  onLoad: function(options) {
    let userInfo = wx.getStorageSync("userInfo");
    let currentRecord = app.globalData.currentRecord;
    this.setData({
      userId: userInfo.id,
      userLogin: app.globalData.hasLogin
    })
    this.getIndexData();
    if (!app.globalData.hasLogin) {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    }
  },
/************************ User defined methods ************* */
  previewImg: function (e) {
    var imgArr = e.currentTarget.dataset.item;
    wx.previewImage({
      current: e.currentTarget.dataset.src,     //当前图片地址
      urls: imgArr,               //所有要预览的图片的地址集合 数组形式
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) { },
    })
  },

  getIndexData: function () {
    let that = this;
    util.request(api.SocicalDynamicList).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          banner: res.data.banner,
          channel: res.data.channel,
          lists: res.data.dynamic
        });
      }
    });
  },

  viewUser: function (e) {
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: "/pages/social/personViewer/personViewer?id=" + item.id
    });
  },

  clapDynamic: function (e) {
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }
    let that = this;
    let item = e.currentTarget.dataset.item;
    let clapItem = {
      id: item.id
    }
    let apiURL = item.loginUserClap ? api.DynamicCancelClap : api.DynamicClap;
    util.request(apiURL, clapItem, "POST")
      .then(function (res) {
        if (res.errno == 0) {
          if (item.loginUserClap) {
            item.loginUserClap = false;
            item.clap = item.clap - 1;
          } else {
            item.loginUserClap = true;
            item.clap = item.clap + 1;
          }
          that.refactorData("dynamic", "clap", item);
        }
      });
  },

  viewDynamic: function (e) {
    let that = this;
    let item = e.currentTarget.dataset.item;
    wx.navigateTo({
      url: "/pages/social/dynamicDetail/dynamicDetail?id=" + item.id
    });
  },

  deleteDynamic: function(e) {
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }
    let that = this;
    let item = e.currentTarget.dataset.item;
    let deleteItem = {
      id: item.id
    }
    wx.showModal({
      title: '',
      content: '确定要删除？',
      success: function (res) {
        if (res.confirm) {
          util.request(api.DynamicDelete, deleteItem, "POST")
            .then(function (res) {
              if (res.errno == 0) {
                that.refactorData("dynamic", "delete", item);
              }
            });
        }
      }
    })
  },

  refactorData: function (flag, operator, data) {
    let dynamics = this.data.lists;
    switch (flag) {
      case "dynamic":
        if (operator === "delete") {
          if (dynamics && dynamics.length > 0) {
            let index = dynamics.findIndex((item) => { return item.id === data.id })
            dynamics.splice(index, 1);
          }
        } else if (operator === "clap") {
          if (dynamics && dynamics.length > 0) {
            let dynamic = dynamics.find((item) => { return item.id === data.id })
            dynamic.clap = data.clap;
            dynamic.loginUserClap = data.loginUserClap;
          }
        } else {
        }
        break;
      default:
        break;
    }
    this.setData({
      lists: dynamics
    })
  }

})