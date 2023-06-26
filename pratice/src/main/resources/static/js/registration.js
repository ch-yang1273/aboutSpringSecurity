$(function() {
    // 권한 등록 form
    $('#add-role-form-btn').click(function () {
        $("#admin-table").load("/admin/role/add .registration", function (){
            $.getScript("/js/registration.js")
        });
    });

    $('.add-hierarchy-form-btn').click(function (event) {
        event.preventDefault();
        const child = $(this).data('child');
        $("#admin-table").load("/admin/role/hierarchy?child=" + encodeURIComponent(child) + " .registration", function (){
            $.getScript("/js/registration.js")
        });
    });

    $('#add-hierarchy-btn').click(function () {
        const childRole = $('#child-role').text();
        const parentRole = $('#parentRole').val();

        $.ajax({
            type: 'POST',
            url: '/api/admin/role/hierarchy',
            contentType: 'application/json',
            data: JSON.stringify({
                childName: childRole,
                parentName: parentRole
            })
        }).always(function (){
            $("#admin-table").load("/admin/role .management", function (){
                $.getScript("/js/registration.js")
            });
        })
    });

    $('#add-role-btn').click(function () {
        const roleName = $('#role-name').val();
        const description = $('#role-description').val();

        $.ajax({
            type: 'POST',
            url: '/api/admin/role/add',
            contentType: 'application/json',
            data: JSON.stringify({
                roleName: roleName,
                description: description
            })
        }).always(function (){
            $("#admin-table").load("/admin/role .management", function (){
                $.getScript("/js/registration.js")
            });
        })
    });

    // 리소스 등록 form
    $('#add-resource-form-btn').click(function () {
        $("#admin-table").load("/admin/resource/add .registration", function (){
            $.getScript("/js/registration.js")
        });
    });

    $('#add-resource-btn').click(function () {
        const orderNum = $('#orderNum').val();
        const resourceType = $('#resourceType').val();
        const resourceName = $('#resourceName').val();
        const httpMethod = $('#httpMethod').val();
        const memberRole = $('#memberRole').val();

        $.ajax({
            type: 'POST',
            url: '/api/admin/resource/add',
            contentType: 'application/json',
            data: JSON.stringify({
                orderNum: orderNum,
                resourceType: resourceType,
                resourceName: resourceName,
                httpMethod: httpMethod,
                memberRole: memberRole
            })
        }).always(function (){
            $("#admin-table").load("/admin/resource .management", function (){
                $.getScript("/js/registration.js")
            });
        })
    });
});