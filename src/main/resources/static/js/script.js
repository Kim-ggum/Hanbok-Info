//window.addEventListener("scroll", function(){
//            var header = document.querySelector("header");
//            header.classList.toggle('sticky', window.scrollY > 0)
//        });
//        var menu = document.querySelector('.header_menu');
//        var menuBtn = document.querySelector('.menu-btn');
//        var closeBtn = document.querySelector('.close-btn');
//        
//        menuBtn.addEventListener("click" , ()=> {
//            menu.classList.add('active');
//        });
//        
//        closeBtn.addEventListener("click" , ()=> {
//            menu.classList.remove('active');
//        });
//        
//        $(document).ready(function () {
//  var swiper = new Swiper(".swiper-container", {
//    autoplay: {
//      delay: 5000,
//      disableOnInteraction: false
//    },
//    speed: 500,
//    loop: true,
//    pagination: {
//      el: ".swiper-pagination",
//      type: "fraction"
//    },
//    navigation: {
//      nextEl: ".swiper-button-next",
//      prevEl: ".swiper-button-prev"
//    },
//    on: {
//      init: function () {
//        $(".swiper-progress-bar").removeClass("animate");
//        $(".swiper-progress-bar").removeClass("active");
//        $(".swiper-progress-bar").eq(0).addClass("animate");
//        $(".swiper-progress-bar").eq(0).addClass("active");
//      },
//      slideChangeTransitionStart: function () {
//        $(".swiper-progress-bar").removeClass("animate");
//        $(".swiper-progress-bar").removeClass("active");
//        $(".swiper-progress-bar").eq(0).addClass("active");
//      },
//      slideChangeTransitionEnd: function () {
//        $(".swiper-progress-bar").eq(0).addClass("animate");
//      }
//    }
//  });
//  $(".swiper-container").hover(function () {
//    swiper.autoplay.stop();
//    $(".swiper-progress-bar").removeClass("animate");
//  }, function () {
//    swiper.autoplay.start();
//    $(".swiper-progress-bar").addClass("animate");
//  });
//});





//한복 스와이프
var swiper = new Swiper(".slide-container", {
        slidesPerView: 4,
        spaceBetween: 30,
        slidesPerGroup: 4,
        loop: true,
        centerSlide: "true",
        grabCursor: "true",
        fade: "true",
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
          dynamicBullets: true,
            
        },
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },

        breakpoints:{
            0:{
                slidesPerView:1,
            },
            520:{
                slidesPerView:2,
            },
            768:{
                slidesPerView:3,
            },
            1000:{
                slidesPerView:3,
            },
        }
      });
//헤더 부분 JS
        window.addEventListener("scroll", function(){
            var header = document.querySelector("header");
            header.classList.toggle('sticky', window.scrollY > 0)
        });
        var menu = document.querySelector('.header_menu');
        var menuBtn = document.querySelector('.menu-btn');
        var closeBtn = document.querySelector('.close-btn');

        menuBtn.addEventListener("click" , ()=> {
            menu.classList.add('active');
        });

        closeBtn.addEventListener("click" , ()=> {
            menu.classList.remove('active');
        });

//첫번째 메인컨텐츠 JS
        $(document).ready(function () {
  var swiper = new Swiper(".swiper-container", {
      preloadImages: false,
  // Enable lazy loading
  lazy: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false
    },
    speed: 500,
    loop: true,
    pagination: {
      el: ".swiper-pagination",
      type: "fraction",
      dynamicBullets: true,         
    },
    lazy: {
    loadPrevNext: true,
  },  
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev"
    },
    on: {
      init: function () {
        $(".swiper-progress-bar").removeClass("animate");
        $(".swiper-progress-bar").removeClass("active");
        $(".swiper-progress-bar").eq(0).addClass("animate");
        $(".swiper-progress-bar").eq(0).addClass("active");
      },
      slideChangeTransitionStart: function () {
        $(".swiper-progress-bar").removeClass("animate");
        $(".swiper-progress-bar").removeClass("active");
        $(".swiper-progress-bar").eq(0).addClass("active");
      },
      slideChangeTransitionEnd: function () {
        $(".swiper-progress-bar").eq(0).addClass("animate");
      }
    }
  });
  $(".swiper-container").hover(function () {
    swiper.autoplay.stop();
    $(".swiper-progress-bar").removeClass("animate");
  }, function () {
    swiper.autoplay.start();
    $(".swiper-progress-bar").addClass("animate");
  });
});
// 지도 마커설정, 지도 초기화
window.initMap = function () {
  const map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 37.577363085920275, lng: 126.97346207026874 },
    zoom: 8
  });

  const malls = [
    { label: "1", name: "한복남경복궁점", "lat": 37.57619842448816, lng : 126.97329103946686 },
    { label: "2", name: "대한한복", "lat": 37.57603048877819, lng : 126.97352170944214},
    { label: "3", name: "한복궁소녀", "lat": 37.57640674927298, lng : 126.97307646274567 },
    { label: "4", name: "한복데이", "lat": 37.57656936974584,
  "lng": 126.97411581873894 },
    { label: "5", name: "이리데한복", "lat": 37.57654279779743,
  "lng": 126.9732266664505 },
    { label: "Land_G", name: "광화문", "lat": 37.575909319739566,
  "lng": 126.97680473327637 },
    { label: "Land_2", name: "경회루", "lat": 37.579710107249326,
  "lng": 126.97587668895721 }
  ];

  const bounds = new google.maps.LatLngBounds();
  const infowindow = new google.maps.InfoWindow();

  malls.forEach(({ label, name, lat, lng }) => {
    const marker = new google.maps.Marker({
      position: { lat, lng },
      label,
      map: map
    });
    bounds.extend(marker.position);

    marker.addListener("click", () => {
      map.panTo(marker.position);
      infowindow.setContent(name);
      infowindow.open({
        anchor: marker,
        map
      });
    });
  });
  map.fitBounds(bounds);
};

//body 전체 reveal 효과
window.addEventListener('scroll', reveal);

    function reveal(){
      var reveals = document.querySelectorAll('.reveal');

      for(var i = 0; i < reveals.length; i++){

        var windowheight = window.innerHeight;
        var revealtop = reveals[i].getBoundingClientRect().top;
        var revealpoint = 150;

        if(revealtop < windowheight - revealpoint){
          reveals[i].classList.add('active');
        }
        else{
          reveals[i].classList.remove('active');
        }
      }
    }

