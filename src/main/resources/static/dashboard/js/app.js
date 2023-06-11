const activePage=window.location.pathname;
    const navLinks=document.querySelectorAll('.nav-menu .menu-item a').forEach(link=>{
        if(link.href.includes(`${activePage}`)){
            console.log(`${activePage}`);
            link.classList.add('active');

        }
    })
$('.sidebar-show').click(function(){
    $('.sidebar').animate({marginLeft:0});
});
$('.close-sidebarbtn').click(function(){
    $('.sidebar').animate({marginLeft:"-100%"});
});
function go(url){
    location.href=`${url}`;
}
$(function () {
    $('[data-toggle="popover"]').popover()
  });
  $('.full-page-btn').click(function(){
      let current=$(this).closest('.card');
    current.toggleClass('full-page-card');
    
    if(current.hasClass("full-page-card")){
       
       $(this).html(`<i class="feather-minimize-2 h4"></i>`); 

    }
    else{
        $(this).html(`<i class="feather-maximize-2 h4"></i>`);  
     }
  });
  let screenHeight=$(window).height();
  let currentMenuHeight=$('.nav-menu .active').offset().top;
  if(currentMenuHeight>screenHeight){
      $('.sidebar').animate({
          scrollTop:currentMenuHeight-100
      },1000)
  }
