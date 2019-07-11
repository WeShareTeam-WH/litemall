// 以下是业务服务器API地址
// 本机开发时使用
var WxApiRoot = 'http://localhost:8080/wx/';
// 局域网测试使用
// var WxApiRoot = 'http://192.168.0.101:8080/wx/';
// 云平台部署时使用
// var WxApiRoot = 'http://118.24.0.153:8080/wx/';
// 云平台上线时使用
//  var WxApiRoot = 'https://xynm.store/wx/';

module.exports = {
  IndexUrl: WxApiRoot + 'home/index', //首页数据接口
  CatalogList: WxApiRoot + 'catalog/index', //分类目录全部分类数据接口
  CatalogCurrent: WxApiRoot + 'catalog/current', //分类目录当前分类数据接口

  AuthLoginByWeixin: WxApiRoot + 'auth/login_by_weixin', //微信登录
  AuthLoginByAccount: WxApiRoot + 'auth/login', //账号登录
  AuthLogout: WxApiRoot + 'auth/logout', //账号登出
  AuthRegister: WxApiRoot + 'auth/register', //账号注册
  AuthReset: WxApiRoot + 'auth/reset', //账号密码重置
  AuthRegisterCaptcha: WxApiRoot + 'auth/regCaptcha', //验证码
  AuthBindPhone: WxApiRoot + 'auth/bindPhone', //绑定微信手机号

  StorageUpload: WxApiRoot + 'storage/upload', //图片上传,
  UserIndex: WxApiRoot + 'user/index', //个人页面用户相关信息
  IssueList: WxApiRoot + 'issue/list', //帮助信息

  SocicalDynamicList: WxApiRoot + 'home/list', //动态list,
  DynamicClap: WxApiRoot + 'social/dynamic/clap', //点赞动态
  DynamicAdd: WxApiRoot + 'social/dynamic/add', //点赞ADD
  DynamicDelete: WxApiRoot + 'social/dynamic/delete', //点赞动态
  DynamicCancelClap: WxApiRoot + 'social/dynamic/cancelClap', //取消动态
  SocialDynamicDetail: WxApiRoot + 'social/dynamic/detail', //动态详情
  UserDynamics: WxApiRoot + 'social/dynamic/userDynamics', //用户动态

  CommentsListSelect: WxApiRoot + 'social/comment/list', //加载更多详情的评论
  CommentsReplyListSelect: WxApiRoot + 'social/reply/list', //加载更多评论的回复
  CommentAdd: WxApiRoot + 'social/comment/post', // 发评论
  CommentClap: WxApiRoot + 'social/comment/clap', //点赞评论
  CommentCancelClap: WxApiRoot + 'social/comment/cancelClap', //取消评论
  CommentDelete: WxApiRoot + 'social/comment/delete', //delete评论
  CommentReplyAdd: WxApiRoot + 'social/reply/post', // 发评论的回复
  CommentReplyDelete: WxApiRoot + 'social/reply/delete', //delete评论
};