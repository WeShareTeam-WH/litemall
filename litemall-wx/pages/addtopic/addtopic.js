const util = require('../../utils/util.js');
const api = require('../../config/api.js');
var user = require('../../utils/user.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    files: [],
    picUrls: [],
    hasPicture: false,
    content: ''
  },
  onLoad: function (options) {
    user.checkLogin().then(res => {
      //跳转到登陆页面
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    }).catch(() => {
      //do nothing
    });
  },
  chooseImage: function (e) {
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
  upload_life_message: function() {
    let that = this;

    util.request(api.UploadLifeMessage,{
      content: that.data.content,
      hasPicture: that.data.hasPicture,
      picUrls: that.data.picUrls
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        wx.showToast({
          title: '发布成功',
          icon: 'success',
          duration: 3000
        });
      }
      // 跳转到生活页面
      wx.navigateTo({
        url: "pages/life/life"
      });
    });
  },
  upload: function (res) {
    var that = this;
    const uploadTask = wx.uploadFile({
      url: api.StorageUpload,
      filePath: res.tempFilePaths[0],
      name: 'file',
      success: function (res) {
        var _res = JSON.parse(res.data);
        if (_res.errno === 0) {
          var url = _res.data.url
          that.data.picUrls.push(url)
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

    uploadTask.onProgressUpdate((res) => {
      console.log('上传进度', res.progress)
      console.log('已经上传的数据长度', res.totalBytesSent)
      console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend)
    });
  }
})