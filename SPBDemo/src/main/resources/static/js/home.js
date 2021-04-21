$(function() {
    var reg = $('#reg'),
        forgot = $('#forgot'),
        forgottMail = $('#forgottMail'),
        getForgotMail = $('#getForgotMail'),
        regMail = $('#regMail'),
        regPass = $('#regPass'),
        regNews = $('#regNews'),
        regStart = $('#regStart'),
        regName = $('#regName');
    $(window).load(function() {
        if ("/main/pages/home/" == window.location.pathname) $(".navbar-default .navbar-brand:first").click();
    });
    $(window).resize(function() {
        var nav = $('.navbar-default');
        nav.css({
            left: 0,
            opacity: 1,
            width: 'auto'
        });
    });
    forgot.click(function() {
        $('#forgottenModal').modal();
    });
    reg.click(function() {
        $('#regModal').modal();
    });
    getForgotMail.click(function() {
        forgottMail.tooltip('destroy');
        if (validateEmail(forgottMail.val()) === false) {
            forgottMail.data().title = 'Wrong Email address!';
            forgottMail.tooltip('show');
            return;
        }
        var sender = ajaxCall('/main/pages/home/com.php', 'sendEmailPass', forgottMail.val(), false);
        if (sender != 'undefined' && sender != 'error') {
            forgottMail.data().title = 'e-mail has been sent to ' + sender + '!';
        } else {
            forgottMail.data().title = 'Sorry, you are not a registered user!';
        }
        forgottMail.tooltip('show');
    });
    regStart.click(function() {
        regPass.tooltip('destroy');
        regMail.tooltip('destroy');
        regName.tooltip('destroy');
        if (regName.val() === '') {
            regName.tooltip('show');
            return;
        }
        if (validateEmail(regMail.val()) === false) {
            regMail.data().title = 'Wrong Email address!';
            regMail.tooltip('show');
            return;
        }
        if (validatePassword(regPass.val()) === false) {
            regPass.tooltip('show');
            return;
        }
        param = {};
        param.name = regName.val();
        param.mail = regMail.val();
        param.pass = regPass.val();
        param.news = regNews.prop('checked') === true ? 1 : 0;
        var rep = ajaxCall('/main/pages/home/com.php', 'regStart', param, false);
        regMail.data().title = rep;
        regMail.tooltip('show');
    });
    $('#sendMail').click(function() {
        var send = ajaxCall('com.php', 'sendMail', $('#emailedit').code(), true);
        if (send == "true") {
            $('#sendMail').tooltip('show');
            setTimeout(function() {
                $('#sendMail').tooltip('hide')
            }, 2000);
        }
    });
    // functions
    function validateEmail(email) {
        var re = /\S+@\S+\.\S+/;
        return re.test(email);
    }

    function validatePassword(pass) {
        var regularExpression = /^(?=.*[0-9])(?=.*[!A-Z])[a-zA-Z0-9!@#$%^&*.]{6,16}$/;
        if (pass.length < 6 || pass.length > 16) {
            return false;
        }
        if (!regularExpression.test(pass)) {
            return false;
        }
    }

    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toGMTString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    }

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i].trim();
            if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
        }
        return "";
    }

    function ajaxCall(file, order, param, sync) {
        typeof sync == 'undefined' ? sync = true : sync = false;
        var retVal;
        var fullrow;
        $.ajax({
            url: file,
            type: 'POST',
            async: sync,
            dataType: 'xml/html/script/json/jsonp',
            data: {
                order: order,
                param: param
            },
            complete: function(data, xhr, textStatus) {
                retVal = $.parseJSON(data.responseText);
            },
            success: function(data, textStatus, xhr) {
                retVal = $.parseJSON(data.responseText);
            },
            error: function(xhr, textStatus, errorThrown) {
                //called when there is an error
            }
        });
        return retVal;
    }
    $.fn.outerHTML = function(s) {
        return s ? this.before(s).remove() : jQuery("<p>").append(this.eq(0).clone()).html();
    };
    $('.navbar-default .navbar-brand:first').on({
        click: function() {
            var nav = $('.navbar-default');
            nav.width(nav.width());
            var posClose = nav.eq(0).width() - $('.navbar-default .navbar-brand:first').width() - (nav.eq(0).outerWidth(true) - nav.eq(0).width()) * 2;
            var scrollTo = (nav.position().left == 0) ? posClose : 0;
            nav.eq(0).animate({
                left: scrollTo,
                opacity: (scrollTo == 0) ? 1 : .5
            });
            nav.eq(1).animate({
                left: ((scrollTo == 0) ? 0 : (nav.eq(1).outerWidth(true)) * -1),
                opacity: (scrollTo == 0) ? 1 : .5
            });
        }
    });
});