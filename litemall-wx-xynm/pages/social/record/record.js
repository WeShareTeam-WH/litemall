const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
Page({

  /**
   * 组件的属性列表
   */
  properties: {
    count: { //最多选择图片的张数，默认9张
      type: Number,
      value: 9
    },
    uploadUrl: { //图片上传的服务器路径
      type: String,
      value: ''
    },
    showUrl: { //图片的拼接路径
      type: String,
      value: ''
    }
  },

  /**
  * 页面的初始数据
  */
  data: {
    showIndex: false,
    topics: [],
    submitTopics:[],
    files: [],
    picUrls: [],
    hasPicture: false,
    content: '',
    detailPics: [], //上传的结果图片集合
  },

  onLoad: function (options) {
    let that = this;
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }
    this.initData();
  },

  /******************** User defined ***********************/
  panel: function (e) {
    if (this.data.showIndex) {
      this.setData({
        showIndex: false
      })
    } else {
      this.setData({
        showIndex: true
      })
    }
  },

  initData: function() {
    this.setData({
      topics: [{ title: "襦裙", topicId: 0, dynamicId: 0}, 
        { title: "学校社团", topicId: 0, dynamicId: 0 }, 
        { title: "汉服女裙", topicId: 0, dynamicId: 0 }, 
        { title: "右交领", topicId: 0, dynamicId: 0 },
        { title: "长袍", topicId: 0, dynamicId: 0 },
        { title: "儒家道袍", topicId: 0, dynamicId: 0 },
        { title: "齐胸襦裙", topicId: 0, dynamicId: 0 },
        { title: "襦袴", topicId: 0, dynamicId: 0 },
        { title: "深衣", topicId: 0, dynamicId: 0 },
        { title: "汉服运动", topicId: 0, dynamicId: 0 },]
    })
  },

  addTopic: function(e) {
    let targetItem = e.currentTarget.dataset.item;
    let submitTopics = this.data.submitTopics;
    let findItem = submitTopics.find((item) => {
      return item.title === targetItem.title;
    })
    if (findItem) {
      return;
    } else {
      submitTopics.push(targetItem);
    }
    this.setData({
      submitTopics: submitTopics
    })
  },

  deleteTopic: function(e) {
    let submitTopics = this.data.submitTopics;
    let target = e.currentTarget.dataset.item;
    let index = submitTopics.findIndex((item) => {
      return item.title === target.title;
    })
    submitTopics.splice(index, 1);
    this.setData({
      submitTopics: submitTopics
    })
  },

  chooseImage: function (e) {
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }
    if (this.data.files.length >= 9) {
      util.showErrorToast('只能上传九张图片')
      return false;
    }

    var that = this;
    wx.chooseImage({
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        that.setData({
          files: that.data.files.concat(res.tempFilePaths)
        });
        that.upload(res);
      }
    })
  },
  previewImage: function (e) {
    wx.previewImage({
      current: e.currentTarget.id, // 当前显示图片的http链接
      urls: this.data.files // 需要预览的图片http链接列表
    })
  },
  deleteImage: function (e) {
    var that = this;
    var imgList = that.data.files;
    var index = e.currentTarget.dataset.index;
    wx.showModal({
      title: '提示',
      content: '确定要删除此图片吗？',
      success: function (res) {
        if (res.confirm) {
          imgList.splice(index, 1);
        } else if (res.cancel) {

          return false;
        }
        that.setData({
          files: imgList
        });
      }
    })
  },

  bindInputValue(event) {
    let value = event.detail.value;
    //判断是否超过140个字符
    if (value && value.length > 200) {
      return false;
    }
    this.setData({
      content: event.detail.value,
    })
  },

  upload_life_message: function () {
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }
    let that = this;
    util.request(api.DynamicAdd, {
      title:"",
      content: that.data.content,
      picture: that.data.picUrls,
      topics: that.data.submitTopics
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        wx.showToast({
          title: '发布成功',
          icon: 'success',
          duration: 3000
        });
        app.globalData.currentRecord = res.data;
        that.setData({
          submitTopics: [],
          picUrls: [],
          content: ""
        })
        // 跳转到生活页面
        wx.switchTab({
          url: '/pages/index/index',
        })
      }
    });
  },

  upload: function (res) {
    if (!app.globalData.hasLogin) {
      wx.showToast({
        title: '请先登录~',
      });
      return;
    }

    wx.showLoading({
      title: '上传中...',
      mask: true,
    })
    let that = this;
    let tempFilePaths = res.tempFilePaths;
    for (var i = 0; i < tempFilePaths.length; i++) {
      wx.uploadFile({
        url: api.StorageUpload,
        filePath: tempFilePaths[i],
        name: 'file',
        success: function (res) {
          var _res = JSON.parse(res.data);
          if (_res.errno === 0) {
            var url = _res.data.url
            that.data.picUrls.push(url)
            wx.hideLoading();
            that.setData({
              hasPicture: true,
              picUrls: that.data.picUrls
            })
          }
        },
        fail: function (e) {
          wx.showModal({
            title: '错误',
            content: '上传失败',
            showCancel: false
          })
        },
      });
    }
  }
}) 