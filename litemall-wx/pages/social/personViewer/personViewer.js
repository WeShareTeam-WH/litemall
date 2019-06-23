var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dynamicCount: 0,
    dynamics:[],
    userInfo:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.id) {
      this.getUserSpace(options.id);
    }
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
    //获取用户的登录信息
    if (app.globalData.hasLogin) {
      let userInfo = wx.getStorageSync('userInfo');
      this.setData({
        userInfo: userInfo,
        hasLogin: true
      });
    }
  },

  goLogin() {
    if (!this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    }
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

  /****************************** User defined ********************/
  getUserSpace: function(id){
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.UserDynamics, {
      id: id,
      page: 1,
      size: 10
    })
      .then(function (res) {
        that.setData({
          dynamicCount: res.data.dynamicCount,
          dynamics: res.data.dynamics,
          userInfo: res.data.userInfo
        });
        wx.hideLoading();
      });
  },

  viewDetail: function(e){
    let item = e.currentTarget.dataset.item;
    wx.redirectTo({
      url: '../dynamicDetail/dynamicDetail?id=' + item.id,
    })
  }

})