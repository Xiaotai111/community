/**
 * 提交回复
 */
function post(){
    let questionId = $("#question_id").val();
    let content = $("#comment-content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content){
    if(!content){
        alert("不能回复空内容")
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function (response){
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code == 2003){
                    let isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=2301a7a10ac473fba41e&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                }else{
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
}

function comment(e){
    let commentId = e.getAttribute("data-id");
    let content = $("#input-"+commentId).val();
    comment2target(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e){
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    //获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length != 1){
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else{
            //获得同一回复下的所有二级回复
            $.getJSON("/comment/"+id, function (data){
                $.each(data.data.reverse(), function (index,comment){
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append( $("<img/>",{
                        "class":"media-object img-rounded img-size",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append( $("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append( $("<div/>",{
                        "html":comment.content
                    })).append( $("<div/>",{
                        "class":"menu"
                    })).append( $("<span/>",{
                        "class":"pull-right",
                        "style":"color: #999;margin-top: 5px;",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD hh:mm')
                    }));

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    var commentElement = $("<div>", {
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement)
                });
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}
function selectTag(e){
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if(previous.indexOf(value) == -1){
        if(previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag() {
    $("#select-tag").show();
}