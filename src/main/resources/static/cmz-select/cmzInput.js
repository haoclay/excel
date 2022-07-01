var startDay =0 ;
var endDay =0 ;
var clazz = 'vip2202';
var subCount=1;
var titleDayList = new Array();
var countList = new Array();
var startId = 0;
var endId = 0 ;
var arr = [
  { id: 0, day: '作业名称', dayIndex: '作业索引' }
];
var url = "/stuHomeWork/findTitleAndDayIndexFromDB";

;(function ($) {
  $.fn.cmzSelect = function ({
    id = null,
    currentId = null,
    arr,
    label,
    callback,
    searchList = [],
    liRow = null
  }) {
    // arr         下拉列表数据源
    // label       input展示数据字段
    // callback    选中项的回调函数
    // searchList  模糊搜索的字段
    // liRow         自定义下拉列表dom

    // 当前this
    let that = this;
    // alert("获取arr...")

    // 报错提示
    if (!(arr.length && isType(arr[0], 'Object'))) {
      console.error(new Error('arr格式为[{},{}...],且数组长度至少为1'))
      return
    }

    if (!(label in arr[0])) {
      console.error(new Error('label属性不存在'))
      return
    }

    if (id === null || !(id in arr[0])) {
      console.error(new Error('唯一标识id不能为空，且必须存在于数组对象内'))
      return
    }

    // 初始化dom结构
    that.append(
      `<div class="cmz-select">
      
      <!-- 删除icon -->
      <span class="iconfont icon-close-circle inputImg delImg hide"></span>

      <input class="cmz-input" placeholder="请选择" type="text" />

      <!-- 下箭头icon -->
      <span class="iconfont icon-shanglajiantou inputImg arrowIcon"></span>

      <ul class="list"></ul>
      <ul class="listT">
      <li> 无匹配数据 </li>
      </ul>
      
      </div>`
    )

    // input框的父级
    let $cmzSelect = this.find('.cmz-select')
    // input框
    let $cmzInput = $cmzSelect.find('input.cmz-input')
    // 下拉框list
    let $list = $cmzSelect.find('ul.list')
    // 无匹配数据时的下拉框
    let $listT = $cmzSelect.find('ul.listT')
    // Icon（下箭头）
    let $arrowIcon = $cmzSelect.find('span.arrowIcon')
    // Icon（删除）
    let $delImg = $cmzSelect.find('span.delImg')

    // select选中的项
    let inputVal = ''

    var iid ;

    // 下拉列表dom
    let options = createE('ul', ['list'])

    // 是否修改过一次css
    let cssFlag = false

    // 下拉框列表
    if (liRow === null) {
      arr.forEach(row => {
        let $li = createE('li', '', row[label])
        options.appendChild($li)
      })
    } else {
      // 正则匹配胡子语法
      let reg = /{{(.+?)}}/g
      // 下拉框宽度
      let w = 0
      let flag = false
      arr.forEach(row => {
        let liDom =
          row[id] === currentId
            ? createE('li', ['clearfix', 'selected'])
            : createE('li', ['clearfix'])

        for (let child of liRow) {
          child = child.trim()
          child = child.replace(reg, function () {
            return row[arguments[1].trim()]
          })
          let childDom = beE(child)
          liDom.appendChild(childDom)
          if (flag === false) w += Number(childDom.style.width.split('p')[0])
        }
        if (!flag) flag = !flag
        options.appendChild(liDom)
      })
      // 设置下拉框宽度
      options.style.width = w + 40 + 'px'
    }

    // 替换展示列表
    $list.replaceWith(options)

    // 重新获取元素
    $list = $cmzSelect.find('ul.list')

    // 样式：下拉框定位
    $list.css({ top: $cmzInput.outerHeight() })
    $listT.css({ top: $cmzInput.outerHeight() })

    // 初始化已有数据
    init()

    // 点击显示下拉框ul块
    $cmzSelect.on('click', function (e) {
      // 清空输入框内容
      $cmzInput.val('')

      if (!cssFlag) {
        cssFlag = true
        // 样式：提示块 宽度调整
        if ($cmzInput.outerWidth() > $list.outerWidth()) {
          $list.css({ width: $cmzInput.outerWidth() })
        }
        if ($cmzInput.outerWidth() > $listT.outerWidth()) {
          $listT.css({ width: $cmzInput.outerWidth() })
        }
      }

      // 下拉列表隐藏时
      if ($list.is(':hidden') && $listT.is(':hidden')) {
        // 展示全部下拉列表
        showList()
      } else {
        // 隐藏下拉框并重新赋值输入框
        inputBlur()
        // 去除焦点
        $cmzInput.blur()
      }

      // 切换placeholder提示内容
      togglePlaceholder()
      // 阻止事件传递
      e.stopPropagation()
    })



    // 下拉项选中时
    $list.find('li').on('click', function (e) {
      // 当前项索引
      let idx = $list.find('li').index(this)

      // select选中的项
      inputVal = arr[idx][label];
      iid = arr[idx][id];
      if($(this).parents('div#clazz').length == 1){
          clazz = inputVal;
        $.ajax({
          url: '/stuHomeWork/findUpdateTime',
          type: "post",
          data: {'clazz': clazz},
          dataType: 'json',
          success: function (data) {
            if(data != undefined && data!= null){
              $('p.money').text('于'+formatDate(data.updateTime)+'更新');
              $('p.title-info').text(data.startTime+'~'+data.endTime);
              $('p.title-info').css({'color':'#00ff66'});
            }else {
              $('p.money').text('xxxx-xx-xx');
              $('p.title-info').css({'color':'tomato'});
              $('p.title-info').text('暂无信息披露');
            }

          },
          error:function(data){
            $('p.money').text('xxxx-xx-xx');
            $('p.title-info').css({'color':'tomato'});
            $('p.title-info').text('暂无信息披露');
            }
          })

        var typp = $(this).parents('div#clazz').attr("typp");
        if(typp == 'query'){
          var list1=document.getElementById("subCount");
          if(list1.childNodes.length!=0){
            list1.removeChild(list1.childNodes[0]);
          }
        }else {
          url = "/stuHomeWork/findTitleAndDayIndexFromKTP";
        }
        var list2=document.getElementById("startDay");
        var list3=document.getElementById("endDay");

        if(list2.childNodes.length!=0 ){
          list2.removeChild(list2.childNodes[0]);
          titleDayList = [] ;
        }
       if(list3.childNodes.length!=0){
         list3.removeChild(list3.childNodes[0]);
         countList.length = 0 ;
        }


        $.ajax({
          url: url,
          type:"post",
          data:{'clazz': clazz},
          dataType:'json',
          success:function (data) {
              for (var i = 0; i < data.titleList.length; i++) {
                titleDayList[i] = { id: i, day: data.titleList[i], dayIndex: data.dayIndexList[i] };
                countList[i] = { id: i, count: (i+1) };
              }
            // 选中时触发
            var callback = function (val) {
              console.log(val)
            }

            // 自定义下拉列表：必须传数组，且每项dom的自定义样式必须为行内样式
            // 胡子语法{{ xxx }},括号之间不能用空格，如：{ {} } 无法识别
            var liRow = [
              '<div class="fl" style="width:130px;">{{ day }}</div>',
              '<div class="fl" style="width:90px;">{{dayIndex}}</div>'
            ]



            $('#subCount').cmzSelect({
              // #div为容器
              id: 'id', // 唯一标识
              currentId: 0, // 当前选择的数据
              arr: countList, // 数据源
              label: 'count', // input展示数据
              searchList: ['count'],// 模糊查询字段列表
            })

            $('#startDay').cmzSelect({
              // #div为容器
              id: 'id', // 唯一标识
              currentId: 0, // 当前选择的数据
              arr:titleDayList, // 数据源
              label: 'dayIndex', // input展示数据
              callback, // 回调函数
              liRow, // 自定义下拉列表（可以不传）
              searchList: ['day', 'dayIndex'] // 模糊查询字段列表
            })
            $('#endDay').cmzSelect({
              id: 'id', // 唯一标识
              currentId: 0, // 当前选择的数据
              arr:titleDayList, // 数据源
              label: 'dayIndex', // input展示数据
              callback, // 回调函数
              liRow, // 自定义下拉列表（可以不传）
              searchList: ['day', 'dayIndex'] // 模糊查询字段列表
            })

          }
        });


      }else if($(this).parents('div#subCount').length == 1){
         subCount = inputVal;
      } else if($(this).parents('div#startDay').length == 1){
          startDay = inputVal;
          startId = iid;
      }else if($(this).parents('div#endDay').length == 1){
          endDay = inputVal;
          endId = iid;
      }


      // 调用回调函数
      if (callback) callback(arr[idx])

      // 切换选中样式
      $(this).addClass('selected')
      $(this)
        .siblings('li')
        .removeClass('selected')

      // 赋值input框内容
      $cmzInput.val(inputVal)

      // icon变回下箭头
      $arrowIcon.removeClass('selected')

      // 隐藏下拉框并重新赋值输入框
      inputBlur()
      // 隐藏下拉框ul块
      $list.hide()

      // 阻止事件传递
      e.stopPropagation()

      //sgq
      /* var dayIndexList = new Array();
       var crateDayList = new Array() ;
       var titleDay = {
         id: 0,
         day: '作业名称',
         dayIndex: '作业索引'
       };
     var titleDayList = new Array();
      function TitleDay(id,day,dayIndex){
        this.id = id;
        this.day = day;
        this.dayIndex = dayIndex;
      }*/


    });





    // 鼠标移入input框时
    $cmzInput.on('mouseenter', function (e) {
      // 显示删除Icon（删除）
      if (inputVal !== '') {
        toggleIcon(true)
      }
    })

    // 鼠标移出input框时
    $cmzInput.on('mouseleave', function (e) {
      // 隐藏Icon（删除）
      toggleIcon(false)
    })

    // 鼠标移入Icon（删除）时
    $delImg.on('mouseenter', function (e) {
      // 显示删除Icon（删除）
      if (inputVal !== '') {
        toggleIcon(true)
      }
    })

    // 鼠标点击Icon（删除）时
    $delImg.on('click', function (e) {
      // 清空select选中的项
      inputVal = ''

      // 取消li选中样式
      $list.find('li').removeClass('selected')

      // 隐藏下拉框并重新赋值输入框
      inputBlur()
      // 隐藏Icon（删除）
      toggleIcon(false)
      // 切换placeholder提示内容
      togglePlaceholder()
      // 调用回调函数
      if (callback) callback('')
      // 阻止事件传递
      e.stopPropagation()
    })

    // 列表hover时，切换背景
    $list.find('li').on('mouseenter mouseleave', function (e) {
      $(this).toggleClass('on')
      // 阻止事件传递
      e.stopPropagation()
    })

    // input焦点丢失时，除点击选项外隐藏下拉列表
    $cmzInput.on('blur', function () {
      let flag = $list.find('li').hasClass('on')
      if (!flag) inputBlur()
    })

    // 输入时激活函数
    $cmzInput.on('input', function () {
      onInputSearch(this.value)
    })

    function formatDate(str){
      let date = new Date(str);
      let year = date.getFullYear();
      let month= date.getMonth() + 1;
      month= month < 10 ? ('0' + month) : month;
      let day = date.getDate();
      day = day < 10 ? ('0' + day ) : day ;
      let h = date.getHours();
      h = h < 10 ? ('0' + h) : h;
      let m = date.getMinutes();
      m = m < 10 ? ('0' + m) : m;
      let s = date.getSeconds();
      s = s < 10 ? ('0' + s) : s;
      return year + '-' + month + '-' + day + ' ' + h + ':' + m + ':' + s;
    }


    // 切换Icon
    function toggleIcon (isShow = true) {
      if (isShow) {
        $delImg.show()
        $arrowIcon.hide()
      } else {
        $delImg.hide()
        $arrowIcon.show()
      }
    }

    // 隐藏下拉框并重新赋值输入框
    function inputBlur () {
      // 重新复制输入框内容
      $cmzInput.val(inputVal)
      // 隐藏下拉框ul块
      $list.hide()
      $listT.hide()
      // icon变回下箭头
      $arrowIcon.removeClass('selected')
    }

    // input框输入内容时
    function onInputSearch (val) {
      var count = 0
      let $lis = $cmzSelect.find('ul.list li')
      if (val != '') {
        $lis.each(function (i, v) {
          // 下拉框每项内容
          var contentValue = $(this).text()

          let flag
          let rowObj = arr[i]

          // 模糊查询配置项存在时
          if (isType(searchList, 'Array') && searchList.length) {
            // 其中一个匹配项符合
            flag = searchList.some(key =>
              rowObj[key]
                ? rowObj[key].toLowerCase().indexOf(val.toLowerCase()) !== -1
                : false
            )
          } else {
            flag = contentValue.toLowerCase().indexOf(val.toLowerCase()) !== -1
          }

          // 模糊查询
          if (flag) {
            $(this).show()
          } else {
            $(this).hide()
            count++
          }
        })
        // 当无匹配项时 隐藏下拉框并显示无匹配项
        if (count === $lis.length) {
          $list.hide()
          $listT.show()
        } else {
          $list.show()
          $listT.hide()
        }
      } else {
        // 展示下拉列表
        showList()
      }
    }

    // 展示下拉列表
    function showList () {
      let $lis = $cmzSelect.find('ul.list li')
      $lis.each(function (i) {
        $(this).show()
      })
      $list.show()
      $listT.hide()
      // icon变为上箭头
      $arrowIcon.addClass('selected')
    }

    // 切换placeholder提示内容
    function togglePlaceholder () {
      // placeholder提示
      if (inputVal) {
        // 存在选中项时赋值
        $cmzInput.attr({ placeholder: inputVal })
      } else {
        $cmzInput.attr({ placeholder: '请选择' })
      }
    }

    // 类型判断函数
    function isType (target, type) {
      return Object.prototype.toString.call(target).slice(8, -1) === type
    }

    // 创建元素
    function createE (tab, cls = null, txt) {
      let t = document.createElement(tab)
      if (cls.length) {
        cls.forEach(v => t.classList.add(v))
      }
      if (txt) t.innerText = txt
      return t
    }

    // 字符串转换成dom
    function beE (template) {
      let tempNode = document.createElement('div')
      tempNode.innerHTML = template
      return tempNode.firstChild
    }

    // 初始化已有数据
    function init () {
      let obj = arr.find(v => v[id] === currentId)
      if (obj) {
        inputVal = obj[label]
      }
      $cmzInput.val(inputVal)
    }
  }
})(jQuery)
