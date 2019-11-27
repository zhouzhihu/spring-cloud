var AlertTips = function () {
    if (this instanceof AlertTips) {
        var that = this;
        // 提示类型文字赋值
        this.alertTips = function (type, text) {
            var _color = "#666666";
            var _background = "#edf2fc";
            var _title = "提示";
            var _text = text;
            var _type = type;
            if (_type == "error") {
                // _title = "错误";
                _color = "#f56c6c";
                _background = "#fef0f0";
            } else if (_type == "info") {
                // _title = "提示";
                _color = "#666666";
                _background = "#edf2fc";
            } else if (_type == "warning") {
                // _title = "警告";
                _color = "#e6a23c";
                _background = "#fdf6ec";
            } else if (_type == "success") {
                // _title = "成功";
                _color = "#67c23a";
                _background = "#f0f9ec";
            }
            $(".hyhtContainer-tips").css("transform","translateY(100px)").css({"color":_color,"background":_background})
            $(".hyhtContainer-tips_title").html(_title + "：");
            $(".hyhtContainer-tips_text").html(_text);
            setTimeout(function () {
                $(".hyhtContainer-tips").css("transform","translateY(0)");
            }, 2000)
        };
        // 创建html
        this.createHtml = function () {
            var html = "";
            var _style = "background: #fff;min-width: 350px;box-sizing:border-box;border-radius: 4px;position: fixed;left: calc( 50% - 175px);top: -80px;background: #edf2fc;overflow: hidden;transition: opacity .3s,transform .4s;padding: 15px 15px 15px 20px;display: flex;z-index:2000;"
            if (!$(".hyhtContainer-tips").length>0) {
                html +=  '<div class="hyhtContainer-tips" style="'+_style+'">'
                html += '   <span class="hyhtContainer-tips_title">提示：</span>'
                html += '   <span class="hyhtContainer-tips_text">请输入！</span>'
                html += '</div>'
                return html;
            }
        };
        // 初始化init
        this.init = function (selector) {
            $(selector).append(that.createHtml());
        }
    } else {
        return new AlertTips();
    }
}
$.fn.extend({
    getAlertTips: function () {
        return new AlertTips();
    }
});