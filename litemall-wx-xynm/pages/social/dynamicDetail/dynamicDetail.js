var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();
const defaultPlaceholder = "我来发表评论";
Page({

  /**
   * 页面的初始数据
   */
  data: {
      currentId: 1,
      userId:0,
      social:{},
      comment:{},
      reply:{},
      inputValue:"",
      placeholder: defaultPlaceholder,
      flag:"comment", // comment or reply
      focused: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let userInfo = wx.getStorageSync("userInfo");
    this.setData({
      currentId: options.id,
      userId: userInfo.id
    })
    this.getCurrentDynamicDetail(options.id);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  /************************************* User define function */

  getCurrentDynamicDetail: function (id) {
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.SocialDynamicDetail, {
      id: id,
      page: 1,
      size: 10
    })
      .then(function (res) {
        that.setData({
          social: res.data
        });
        wx.hideLoading();
      });
  },

  viewUser: function (e) {
    let item = e.currentTarget.dataset.item;
    wx.redirectTo({
      url: "/pages/social/personViewer/personViewer?id=" + item.id
    });
  },

  previewImg: function (e) {
    console.log(e.currentTarget.dataset.index);
    var index = e.currentTarget.dataset.index;
    var imgArr = this.data.imgArr;
    wx.previewImage({
      current: imgArr[index],     //当前图片地址
      urls: imgArr,               //所有要预览的图片的地址集合 数组形式
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) { },
    })
  },

  backHome: function() {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },

  inputbindfocus: function() {
    this.setData({
      focused: true
    })
  },

  inputbindblur: function(e) {
    this.setData({
      focused: false
    })
  },

  bindinput: function(e) {
    this.setData({
      inputValue: e.detail.value
    })
  },
  
  replyComment: function(e) {
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }
    let userInfo = wx.getStorageSync("userInfo");
    let item = e.currentTarget.dataset.item;
    if (userInfo && item && item.user) {
      let reply = {
        dynamicId: item.dynamicId,
        commentId: item.id,
        replyFrom: userInfo.id,
        replyTo: item.user.id
      }
      this.setData({
        flag: "reply",
        focused: true,
        reply: reply,
        placeholder: "回复 " + item.user.nickname + ":"
      })
    }
  },

  replyReply: function(e) {
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }
    let userInfo = wx.getStorageSync("userInfo");
    let item = e.currentTarget.dataset.item;
    if (userInfo && item && item.replyFromUser) {
      let reply = {
        dynamicId: item.dynamicId,
        commentId: item.commentId,
        replyFrom: userInfo.id,
        replyTo: item.replyFromUser.id
      }
      this.setData({
        flag: "reply",
        focused: true,
        reply: reply,
        placeholder: "回复 " + item.replyFromUser.nickname + ":"
      })
    }
  },

  sendReply: function() {
    let that = this;
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }
    let userInfo = wx.getStorageSync("userInfo");
    if (this.data.inputValue) {
      switch (this.data.flag){
        case 'comment':
          let comment = {
            dynamicId: this.data.currentId,
            userId: userInfo.id,
            content: this.data.inputValue
          }
          util.request(api.CommentAdd, comment, "POST")
          .then(function (res) {
              if (res.errno == 0){
                that.refactorData("comment", "add", res.data);
              }
          });
        break;
        case 'reply':
          let reply = this.data.reply;
          reply.content = this.data.inputValue;
          util.request(api.CommentReplyAdd, reply, "POST")
            .then(function (res) {
              if (res.errno == 0) {
                that.refactorData("reply", "add", res.data);
              } 
            });
        break;
        default:
        break;
      }
    } else {
      wx.showToast({
        title: '评论为空~',
      });
    }
  },

  clapComment : function (e) {
    let that = this;
    let item = e.currentTarget.dataset.item;
    let clapItem = {
      id: item.id
    }
    let apiURL = item.loginUserClap ? api.CommentCancelClap : api.CommentClap;
    util.request(apiURL, clapItem, "POST")
      .then(function (res) {
        if (res.errno == 0) {
          if (item.loginUserClap) {
            item.loginUserClap = false;
            item.clap = item.clap-1;
          } else {
            item.loginUserClap = true;
            item.clap = item.clap + 1;
          }
          that.refactorData("comment", "clap", item);
        } 
      });
  },

  deleteComment : function (e) {
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
          util.request(api.CommentDelete, deleteItem, "POST")
            .then(function (res) {
              if (res.errno == 0) {
                that.refactorData("comment", "delete", item);
              }
            });
        }
      }
    })
  },

  deleteReply : function (e) {
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
          util.request(api.CommentReplyDelete, deleteItem, "POST")
            .then(function (res) {
              if (res.errno == 0) {
                that.refactorData("reply", "delete", item);
              }
            });
        }
      }
    })
  },

  refactorData : function(flag, operator, data) {
    let socialData = this.data.social;
    let inputValue = this.data.inputValue;
    let socialflag = this.data.flag;
    let placeholder = this.data.placeholder;
    switch (flag) {
      case "comment":
        if (operator === "add") {
          if (socialData.comments) {
            socialData.comments.unshift(data);
          } else {
            socialData.comments = [];
            socialData.comments.unshift(data);
          }
          inputValue = "";
          socialflag="comment";
          placeholder=defaultPlaceholder;
        } else if (operator === "delete") {
          if (socialData.comments && socialData.comments.length > 0) {
            let index = socialData.comments.findIndex((item) => { return item.id === data.id })
            socialData.comments.splice(index, 1);
          }
        } else if (operator === "clap"){
          if (socialData.comments && socialData.comments.length > 0) {
            let comment = socialData.comments.find((item) => { return item.id === data.id })
            comment.clap = data.clap;
            comment.loginUserClap = data.loginUserClap;
          }
        } else {
        }
        break;
      case "reply":
        if (operator === "add") {
          let comment = socialData.comments.find((item) => { return item.id === data.commentId })
          if (comment && comment.replys) {
            comment.replys.push(data);
          } else if (comment){
            comment.replys = []
            comment.replys.push(data);
          }
          inputValue = "";
          socialflag = "comment";
          placeholder = defaultPlaceholder;
        } else if (operator === "delete") {
          let comment = socialData.comments.find((item) => { return item.id === data.commentId})
           let index = comment.replys.findIndex((item) => {return item.id === data.id})
           comment.replys.splice(index, 1)
        } else {
          //
        }
        break;
      default:
        break;
    }
    this.setData({
      social: socialData,
      focused: false,
      inputValue: inputValue,
      flag: socialflag,
      placeholder: placeholder
    })
  }

})