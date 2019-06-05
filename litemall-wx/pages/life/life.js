const util = require('../../utils/util.js');
const api = require('../../config/api.js');

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabs: [
      {
        name: '全部',
        value: 'all'
      },
      {
        name: '汉服圈',
        value: 'han'
      },
      {
        name: 'C服圈',
        value: 'c'
      },
      {
        name: '洛丽塔圈',
        value: 'luoli'
      }
    ],
    currentTab: 'all',
    topics: {
      all: {
        lists: [
          {
            detailUrl: 'www.baidu.com',
            id: '1',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'simon',
              description: '桃花得气美人中'
            },
            comment_number: '1',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          },
          {
            detailUrl: 'www.baidu.com',
            id: '2',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'tom',
              description: '桃花得气美人中'

            },
            comment_number: '222',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          },
          {
            detailUrl: 'www.baidu.com',
            id: '3',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'lawrence',
              description: '桃花得气美人中'
            },
            comment_number: '3',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          }
        ],
        nextPage: 1,
        isLoading: false,
        isNoData: false,
        isInit: false,
      },
      han: {
        lists: [
          {
            detailUrl: 'www.baidu.com',
            id: '1',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'simon',
              description: '桃花得气美人中'
            },
            comment_number: '1',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15',
            description: '桃花得气美人中'
          },
          {
            detailUrl: 'www.baidu.com',
            id: '2',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'tom',
              description: '桃花得气美人中'
            },
            comment_number: '2',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          },
          {
            detailUrl: 'www.baidu.com',
            id: '3',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'lawrence',
              description: '桃花得气美人中'
            },
            comment_number: '3',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          },
        ],
        nextPage: 1,
        isLoading: false,
        isNoData: false,
        isInit: false,
      },
      c: {
        lists: [
          {
            detailUrl: 'www.baidu.com',
            id: '1',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'simon',
              description: '桃花得气美人中'
            },
            comment_number: '12',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          },
          {
            detailUrl: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
            id: '2',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'tom',
              description: '桃花得气美人中'
            },
            comment_number: '2',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          },
          {
            detailUrl: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
            id: '3',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'lawrence',
              description: '桃花得气美人中'
            },
            comment_number: '3',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          }
        ],
        nextPage: 1,
        isLoading: false,
        isNoData: false,
        isInit: false,
      },
      luoli: {
        lists: [
          {
            detailUrl: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
            id: '1',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'simon',
              description: '桃花得气美人中'
            },
            comment_number: '2019-06-15',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          },
          {
            detailUrl: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
            id: '2',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'tom',
              description: '桃花得气美人中'
            },
            comment_number: '2019-06-15',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          },
          {
            detailUrl: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
            id: '3',
            author: {
              avatar_url: 'https://wx.qlogo.cn/mmopen/vi_32/24Yp0XXJlcj8vNYjJswGsfvR5vgfgiawpur4ef4tDRynuN6AC7dLS314fNO2R3olFvqTGuTt1Iw5XPibKXQF45yQ/132',
              loginname: 'lawrence',
              description: '桃花得气美人中'
            },
            comment_number: '2019-06-15',
            title: 'This is test',
            content: 'This is test content data',
            create_at: '2019-06-15'
          }
        ],
        nextPage: 1,
        isLoading: false,
        isNoData: false,
        isInit: false,
      }
    },
  },
  getLifeData: function () {
    let that = this;
    util.request(api.LifeUrl).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          //articles: res.data.articlesList
        });
      }
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getLifeData();
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading();
    this.getLifeData();
    wx.hideNavigationBarLoading();
    wx.stopPullDownRefresh();
  }
})