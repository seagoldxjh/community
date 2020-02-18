/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        $("#error_msg").text("不能回复空内容~~~");
        $('#myModal').modal('show')
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            console.log(response)
            if (response.code == 0) {
                // $("#comment_section").hide();
                window.location.reload();
            } else {
                $("#error_msg").text(response.msg);
                $('#myModal').modal('show');
            }
        },
        dataType: "json"
    });
}



/**
 * 收藏问题
 */
function collection(btn){
    console.log(btn.value);
    var questionId = btn.value;
    $.ajax({
        type : "GET",
        url : "/collect",
        data : {
            "questionId" : questionId
        },
        success: function (msg) {
            $('#btn1').text("已收藏");
            $('#btn1').attr("disabled","disabled");
        }
    })
}


/**
 * 进行点赞
 */
function like(obj) {
    var likedUserId = obj.getAttribute("data-id");
    var flag =  $(obj).hasClass("liked");
    var liked_count = Number($(obj).find('span').eq(1).text())
    if (!flag){
        $.ajax({
            type : "GET",
            url : "/like/" + likedUserId,
            success: function (msg) {
                console.log(msg)
                $(obj).addClass("liked")
                $(obj).find('span').eq(1).text(liked_count+1)
            }
        })
    } else {
        $.ajax({
            type : "GET",
            url : "/unlike/" + likedUserId,
            success: function (msg) {
                console.log(msg)
                $(obj).removeClass("liked")
                $(obj).find('span').eq(1).text(liked_count-1)
            }
        })
    }

}


/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                // console.log(data)
                console.log(data.data.reverse())

                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.headImg
                    }));

                    // console.log(timetrans(comment.gmtCreate))

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": timetrans(comment.gmtCreate)
                            // moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}


function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}


function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

function timetrans(date){
    var date = new Date(date);//如果date为13位不需要乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
    return Y+M+D+h+m+s;
}





