(function () {
    var _on = $.fn.on,
        _off = $.fn.off,
        _trigger = $.fn.trigger;

    $.fn.on = function (event, selector, data, callback, one) {
        if (event == "click") {
            return _on.call(this, 'tap', selector, data, callback, one);
        } else {
            return _on.call(this, event, selector, data, callback, one);
        }
    };
    $.fn.trigger = function (event, args) {
        if (event == "click") {
            return _trigger.call(this, 'tap', args);
        } else {
            return _trigger.call(this, event, args);
        }
    };
    $.fn.off = function (event, selector, callback) {
        if (event == "click") {
            return _off.call(this, 'tap', selector, callback);
        } else {
            return _off.call(this, event, selector, callback);
        }
    }
})();

(function ($) {

    /* SlideDown */
    $.fn.slideDown = function (duration) {

        // get the element position to restore it then
        var position = this.css('position');

        // show element if it is hidden
        this.show();

        // place it so it displays as usually but hidden
        this.css({
            position: 'absolute',
            visibility: 'hidden'
        });

        // get naturally height, margin, padding
        var marginTop = this.css('margin-top');
        var marginBottom = this.css('margin-bottom');
        var paddingTop = this.css('padding-top');
        var paddingBottom = this.css('padding-bottom');
        var height = this.css('height');
        var _me = this;
        // set initial css for animation

        // set initial css for animation
        this.css({
            position: position,
            visibility: 'visible',
            overflow: 'hidden',
            height: 0,
            marginTop: 0,
            marginBottom: 0,
            paddingTop: 0,
            paddingBottom: 0
        });

        // animate to gotten height, margin and padding

        window.setTimeout(function () {
            _me.animate({
                height: height,
                marginTop: marginTop,
                marginBottom: marginBottom,
                paddingTop: paddingTop,
                paddingBottom: paddingBottom
            }, duration);
        }, 0);
    };

    /* SlideUp */
    $.fn.slideUp = function (duration) {

        // active the function only if the element is visible
        if (this.height() > 0) {

            var target = this;

            // get the element position to restore it then
            var position = target.css('position');

            // get the element height, margin and padding to restore them then
            var height = target.css('height');
            var marginTop = target.css('margin-top');
            var marginBottom = target.css('margin-bottom');
            var paddingTop = target.css('padding-top');
            var paddingBottom = target.css('padding-bottom');

            this.css({
                visibility: 'visible',
                overflow: 'hidden',
                height: height,
                marginTop: marginTop,
                marginBottom: marginBottom,
                paddingTop: paddingTop,
                paddingBottom: paddingBottom
            });

            // animate element height, margin and padding to zero
            target.animate({
                    height: 0,
                    marginTop: 0,
                    marginBottom: 0,
                    paddingTop: 0,
                    paddingBottom: 0
                },
                {
                    // callback : restore the element position, height, margin and padding to original values
                    duration: duration,
                    queue: false,
                    complete: function () {
                        target.hide();
                        target.css({
                            visibility: 'visible',
                            overflow: 'hidden',
                            height: height,
                            marginTop: marginTop,
                            marginBottom: marginBottom,
                            paddingTop: paddingTop,
                            paddingBottom: paddingBottom
                        });
                    }
                });
        }
    };

    /* SlideToggle */
    $.fn.slideToggle = function (duration) {

        // if the element is hidden, slideDown !
        if (this.height() == 0) {
            this.slideDown();
        }
        // if the element is visible, slideUp !
        else {
            this.slideUp();
        }
    };

    $.fn.prevAll = function (selector) {
        var prevEls = [];
        var el = this[0];
        if (!el) return $([]);
        while (el.previousElementSibling) {
            var prev = el.previousElementSibling;
            if (selector) {
                if ($(prev).is(selector)) prevEls.push(prev);
            }
            else prevEls.push(prev);
            el = prev;
        }
        return $(prevEls);
    };

    $.fn.nextAll = function (selector) {
        var nextEls = [];
        var el = this[0];
        if (!el) return $([]);
        while (el.nextElementSibling) {
            var next = el.nextElementSibling;
            if (selector) {
                if ($(next).is(selector)) nextEls.push(next);
            }
            else nextEls.push(next);
            el = next;
        }
        return $(nextEls);
    };

    $.ajaxSettings.timeout = 15000;

})(Zepto);