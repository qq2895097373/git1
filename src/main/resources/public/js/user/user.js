layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    /**
     * ⽤户列表展示
     */
    var  tableIns = table.render({
        elem: '#userList',
        url : ctx+'/user/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'userName', title: '⽤户名', minWidth:50, align:"center"},
            {field: 'email', title: '⽤户邮箱', minWidth:100, align:'center'},
            {field: 'phone', title: '⽤户电话', minWidth:100, align:'center'},
            {field: 'trueName', title: '真实姓名', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });


    /**
     * 绑定搜索按钮的点击事件
     */
    $(".search_btn").on("click",function(){
        table.reload("userListTable",{
            page: {
                curr: 1 //重新从第 1 ⻚开始
            },
            where: {
                userName: $("input[name='userName']").val(),  //⽤户名
                email: $("input[name='email']").val(),  //邮箱
                phone: $("input[name='phone']").val()  //⼿机号
            }
        })
    });





    /**
     * 头部⼯具栏事件
     */
    table.on("toolbar(users)", function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case "add":
                openAddOrUpdateUserDialog();
                break;
            case "del":
                deleteUser(checkStatus.data);
                break;
        }
    });
    /**
     * 打开⽤户添加或更新对话框
     */
    function openAddOrUpdateUserDialog(userId) {
        var url = ctx + "/user/addOrUpdateUserPage";
        var title = "⽤户管理-⽤户添加";

        if(userId!= undefined){
            url = url + "?id="+userId;
            title = "⽤户管理-⽤户更新";
        }

        layui.layer.open({
            title : title,
            type : 2,
            area:["650px","400px"],
            maxmin:true,
            content : url
        });
    }

    /**
     * 批量删除⽤户
     * @param datas
     */
    function deleteUser(datas) {
        if(datas.length == 0){
            layer.msg("请选择删除记录!", {icon: 5});
            return;
        }
        layer.confirm('确定删除选中的⽤户记录？', {
            btn: ['确定','取消'] //按钮
        }, function(index){
            layer.close(index);
            var ids= "ids=";
            for(var i=0;i<datas.length;i++){
                if(i<datas.length-1){
                    ids=ids+datas[i].id+"&ids=";
                }else {
                    ids=ids+datas[i].id
                }
            }
            $.ajax({
                type:"post",
                url:ctx + "/user/delete",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        tableIns.reload();
                    }else{
                        layer.msg(data.msg, {icon: 5});
                    }
                }
            })
        });
    }

    /**
     * ⾏监听事件
     */
    table.on("tool(users)", function(obj){
        var layEvent = obj.event;
        // 监听编辑事件
        if(layEvent === "edit") {
            openAddOrUpdateUserDialog(obj.data.id);
        }
        else if(layEvent === "del") {
            // 监听删除事件
            layer.confirm('确定删除当前⽤户？', {icon: 3, title: "⽤户管理"}, function (index) {
                $.post(ctx + "/user/delete",{ids:obj.data.id},function (data) {
                    if(data.code==200){
                        layer.msg("操作成功！");
                        tableIns.reload();
                    }else{
                        layer.msg(data.msg, {icon: 5});
                    }
                });
            });
        }
    });


});