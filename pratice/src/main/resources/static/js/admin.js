$(function() {
    const buttons = ['#user-management-btn', '#authorization-management-btn', '#resource-management-btn'];

    // index : 배열 순서, buttonId : 해당 순서의 값
    $.each(buttons, function(index, buttonId) {
        $(buttonId).click(function() {
            console.log(buttonId + " click");
            // 모든 버튼에서 "btn-primary"를 제거하고, "btn-secondary"를 추가
            $.each(buttons, function(index, buttonId) {
                $(buttonId).removeClass('btn-primary').addClass('btn-secondary');
            });

            // 클릭된 버튼에 "btn-primary"를 추가하고, "btn-secondary"를 제거
            $(buttonId).addClass('btn-primary').removeClass('btn-secondary');

            // 클릭된 버튼에 따라 적절한 테이블을 로드한다.
            switch(buttonId) {
                case '#user-management-btn':
                    $("#admin-table").load("/admin/users .management");
                    break;
                case '#authorization-management-btn':
                    $("#admin-table").load("/admin/role .management", function (){
                        // <script> 태그는 빼고 가져오더라
                        $.getScript("/js/registration.js")
                    });
                    break;
                case '#resource-management-btn':
                    $("#admin-table").load("/admin/resource .management");
                    break;
            }
        });
    });
});