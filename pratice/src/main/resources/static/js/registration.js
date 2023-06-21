$(function() {
    // 권한 등록 form
    $('#add-role-form-btn').click(function () {
        console.log("#add-authorization-form-btn click");
        $("#admin-table").load("/admin/role/add .registration", function (){
            $.getScript("/js/registration.js")
        });
    });

    $('#add-role-btn').click(function () {
        console.log("#add-role-btn click");

        const roleName = $('#role-name').val();
        const description = $('#role-description').val();

        $.ajax({
            type: 'POST',
            url: '/api/admin/authorization/add',
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
});