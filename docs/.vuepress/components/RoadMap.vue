<template>
    <div class="road-map" style="width:1220px; height:1700px; margin:0 auto;">
        <!-- 地图左侧 -->
        <div class="road-map-left" style="position:absolute;width:160px;height:600px;">
            <!-- 空白占位 -->
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:10px;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #000000;color:#ffffff;height:30px;line-height:30px;font-size:12px;font-weight:700px;padding-left:24px;">
                        路书 v1.0 | @小傅哥
                    </div>
                    <div class="category"
                         style="background-color: #999999;color:#ffffff;height:30px;line-height:30px;font-size:12px;font-weight:700px;padding-left:24px;">
                        <a href="https://bugstack.cn/" target="_blank" style="color:black;text-decoration:none;">
                            <span>bugstack.cn</span>
                        </a>
                    </div>
                </div>
            </div>
            <!-- 左转占位 -->
            <div css="row"
                 style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;margin-top:-4px;">
                <div class="left_right" style="position: relative;margin-top:-20px;margin-left:122px;">
                    <div class="left-corner" style="margin: 100px auto;
                border-top-left-radius:60px;
                border-bottom-left-radius:60px;
                box-shadow: 0 0 0 30px #00cc99, 0 0 0 60px #99ff99;
                width: 40px;height:104px;
                clip-path: polygon(-300px 0px, 39px -220px, 39px 220px, 60px 220px);"></div>
                </div>
            </div>
            <!-- 空白占位 -->
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:10px;">
                <div class="column" style="width:150px; min-height:100px;">
                </div>
            </div>
            <!-- 左转占位 -->
            <div css="row"
                 style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;margin-top:-36px;">
                <div class="left_right" style="position: relative;margin-top:-40px;margin-left:122px;">
                    <div class="left-corner" style="margin: 100px auto;
                border-top-left-radius:60px;
                border-bottom-left-radius:60px;
                box-shadow: 0 0 0 30px #00cccc, 0 0 0 60px #33ffcc;
                width: 40px;height:120px;
                clip-path: polygon(-300px 0px, 39px -220px, 39px 220px, 60px 260px);"></div>
                </div>
            </div>
            <!-- 空白占位 -->
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:10px;">
                <div class="column" style="width:150px; min-height:116px;">
                </div>
            </div>
            <!-- 左转占位 -->
            <div css="row"
                 style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;margin-top:-36px;">
                <div class="left_right" style="position: relative;margin-top:-40px;margin-left:122px;">
                    <div class="left-corner" style="margin: 100px auto;
                border-top-left-radius:60px;
                border-bottom-left-radius:60px;
                box-shadow: 0 0 0 30px #DA70D6, 0 0 0 60px #FFBBFF;
                width: 40px;height:104px;
                clip-path: polygon(-300px 0px, 39px -220px, 39px 220px, 60px 260px);"></div>
                </div>
            </div>
            <!-- 空白占位 -->
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:10px;">
                <div class="column" style="width:150px; min-height:100px;">
                </div>
            </div>
            <!-- 左转占位 -->
            <div css="row"
                 style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;margin-top:-36px;">
                <div class="left_right" style="position: relative;margin-top:-40px;margin-left:122px;">
                    <div class="left-corner" style="margin: 100px auto;
                border-top-left-radius:60px;
                border-bottom-left-radius:60px;
                box-shadow: 0 0 0 30px #FFD700, 0 0 0 60px #FFF68F;
                width: 40px;height:104px;
                clip-path: polygon(-300px 0px, 39px -220px, 39px 220px, 60px 260px);"></div>
                </div>
            </div>
            <!-- 空白占位 -->
            <div css="row"
                 style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:10px;margin-top:106px;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                </div>
            </div>
        </div>
        <!-- 地图中间 -->
        <div class="road-map-center" style="position:absolute;width:900px;height:600px;margin-left:160px;">
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #f1c232;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        基础
                    </div>
                    <div class="category"
                         style="background-color: #ffe599;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        计算机基础
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/011-计算机基础/1-计算机网络/images/计算机网络.png">
                            <a href="https://item.jd.com/11253710.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>计算机网络</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/011-计算机基础/2-操作系统/images/操作系统.png">
                            <a href="https://item.jd.com/12006637.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>操作系统</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/011-计算机基础/3-编译原理/images/编译原理.png">
                            <a href="https://item.jd.com/10058776.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>编译原理</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #f1c232;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;"></div>
                    <div class="category"
                         style="background-color: #ffe599;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        编程基础
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/012-编程基础/1-数据结构/images/数据结构.png">
                            <a href="https://bugstack.cn/md/algorithm/data-structures/data-structures.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>数据结构</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/012-编程基础/2-数学逻辑/images/数学逻辑.png">
                            <a href="https://bugstack.cn/md/algorithm/logic/2020-03-14-%E9%87%8E%E8%B7%AF%E5%AD%90%E6%90%9E%E7%AE%97%E6%B3%95%E3%80%8A%E4%B8%A4%E6%95%B0%E4%B9%8B%E5%92%8C%E3%80%8B%EF%BC%8C%E5%B8%A6%E7%9D%80%E5%B0%8F%E7%99%BD%E5%88%B7%E9%9D%A2%E8%AF%95.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>数学逻辑</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/012-编程基础/3-设计模式/images/设计模式.png">
                            <a href="https://bugstack.cn/md/develop/design-pattern/2020-05-20-%E9%87%8D%E5%AD%A6Java%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E3%80%8A%E5%AE%9E%E6%88%98%E5%B7%A5%E5%8E%82%E6%96%B9%E6%B3%95%E6%A8%A1%E5%BC%8F%E3%80%8B.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>设计模式</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #f1c232;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;"></div>
                    <div class="category"
                         style="background-color: #ffe599;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        后端语言
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/013-后端语言/1-C语言/images/c语言.png">
                            <a href="https://item.jd.com/12627795.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>C语言</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/013-后端语言/2-Java/images/java.png">
                            <a href="https://item.jd.com/13122155.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>Java</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #f1c232;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;"></div>
                    <div class="category"
                         style="background-color: #ffe599;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        前端语言
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/014-前端语言/1-HTML/images/HTML.png">
                            <a href="https://www.runoob.com/html/html-tutorial.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>HTML</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/014-前端语言/2-JavaScript/images/JavaScript.png">
                            <a href="https://item.jd.com/1049342942.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>JavaScript</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/014-前端语言/3-CSS/images/CSS.png">
                            <a href="https://www.runoob.com/css/css-tutorial.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>CSS</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/01-基础/014-前端语言/4-VUE/images/VUE.png">
                            <a href="https://item.jd.com/13611922.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>VUE</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FF7F00;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        设计
                    </div>
                    <div class="category"
                         style="background-color: #FFA07A;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        PPT
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/02-设计/021-PPT/1-架构图/images/架构图.png">
                            <a href="https://www.microsoft.com/en-ww/microsoft-365/powerpoint" target="_blank" style="color:black;text-decoration:none;">
                                <span>架构图</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FF7F00;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;"></div>
                    <div class="category"
                         style="background-color: #FFA07A;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        draw.io
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/02-设计/022-draw/1-类图/images/类图.png">
                            <a href="https://github.com/jgraph/drawio-desktop/releases" target="_blank" style="color:black;text-decoration:none;">
                                <span>类图</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/02-设计/022-draw/2-流程图/images/流程图.png">
                            <a href="https://github.com/jgraph/drawio-desktop/releases" target="_blank" style="color:black;text-decoration:none;">
                                <span>流程图</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/02-设计/022-draw/3-时序图/images/时序图.png">
                            <a href="https://github.com/jgraph/drawio-desktop/releases" target="_blank" style="color:black;text-decoration:none;">
                                <span>时序图</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/02-设计/022-draw/4-拓扑图/images/拓扑图.png">
                            <a href="https://github.com/jgraph/drawio-desktop/releases" target="_blank" style="color:black;text-decoration:none;">
                                <span>拓扑图</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #99ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        环境
                    </div>
                    <div class="category"
                         style="background-color: #00cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        代码托管
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/041-代码托管/1-Github/images/Github.png">
                            <a href="https://github.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Github</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/041-代码托管/2-Gitcode/images/Gitcode.png">
                            <a href="https://gitcode.net/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Gitcode</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/041-代码托管/3-Gitee/images/Gitee.png">
                            <a href="https://gitee.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Gitee</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #99ffff;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00ccff;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        设计和开发原则
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/03-架构/033-设计和开发原则/1-领域驱动设计/images/领域驱动设计.png">
                            <a href="https://book.douban.com/subject/26819666/" target="_blank" style="color:black;text-decoration:none;">
                                <span>领域驱动设计</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/03-架构/033-设计和开发原则/2-测试驱动开发/images/测试驱动开发.png">
                            <a href="https://book.douban.com/subject/2208597/" target="_blank" style="color:black;text-decoration:none;">
                                <span>测试驱动开发</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #99ffff;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00ccff;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        架构分层
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/03-架构/032-架构分层/1-MVC/images/MVC.png">
                            <a href="https://zh.m.wikipedia.org/zh-hans/MVC" target="_blank" style="color:black;text-decoration:none;">
                                <span>MVC</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/03-架构/032-架构分层/2-DDD/images/DDD.png">
                            <a href="https://en.wikipedia.org/wiki/Domain-driven_design" target="_blank" style="color:black;text-decoration:none;">
                                <span>DDD</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #99ffff;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        架构
                    </div>
                    <div class="category"
                         style="background-color: #00ccff;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        架构模式
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/03-架构/031-架构模式/1-单体应用/images/单体应用.png">
                            <a href="https://zh.wikipedia.org/zh-hans/%E5%96%AE%E9%AB%94%E5%BC%8F%E6%87%89%E7%94%A8%E7%A8%8B%E5%BC%8F" target="_blank" style="color:black;text-decoration:none;">
                                <span>单体应用</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/03-架构/031-架构模式/2-微服务/images/微服务.png">
                            <a href="https://zh.wikipedia.org/wiki/%E5%BE%AE%E6%9C%8D%E5%8B%99" target="_blank" style="color:black;text-decoration:none;">
                                <span>微服务</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/03-架构/031-架构模式/3-SOA/images/SOA.png">
                            <a href="https://zh.wikipedia.org/wiki/%E9%9D%A2%E5%90%91%E6%9C%8D%E5%8A%A1%E7%9A%84%E4%BD%93%E7%B3%BB%E7%BB%93%E6%9E%84" target="_blank" style="color:black;text-decoration:none;">
                                <span>SOA</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFA07A;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FF7F00;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        Power Designer
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/02-设计/024-PowerDesigner/1-数据库建模/images/数据库建模.png">
                            <a href="https://www.sap.com/products/technology-platform/powerdesigner-data-modeling-tools.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>数据库建模</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFA07A;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;"></div>
                    <div class="category"
                         style="background-color: #FF7F00;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        xmind
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/02-设计/023-Xmind/1-思维导图/images/思维导图.png">
                            <a href="https://xmind.cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>思维导图</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #99ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        版本控制
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/041-版本控制/1-Git/images/Git.png">
                            <a href="https://git-scm.com/book/zh/v2/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Git</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/041-版本控制/2-SVN/images/SVN.png">
                            <a href="https://tortoisesvn.net/docs/release/TortoiseSVN_en/index.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>SVN</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #99ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        开发工具
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/042-开发工具/1-IntelliJ IDEA/images/IDEA.png">
                            <a href="https://www.jetbrains.com/zh-cn/idea/" target="_blank" style="color:black;text-decoration:none;">
                                <span>IntelliJ IDEA</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/042-开发工具/2-JetBrains Fleet/images/Fleet.png">
                            <a href="https://www.jetbrains.com/fleet/" target="_blank" style="color:black;text-decoration:none;">
                                <span>JetBrains Fleet</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/042-开发工具/3-Navicat/images/Navicat.png">
                            <a href="https://www.navicat.com.cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Navicat</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #99ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        依赖管理
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/043-依赖管理/1-Maven/images/Maven.png">
                            <a href="https://maven.apache.org/download.cgi" target="_blank" style="color:black;text-decoration:none;">
                                <span>Maven</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/043-依赖管理/2-Gradle/images/Gradle.png">
                            <a href="https://gradle.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Gradle</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #99ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        脚手架
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/044-脚手架/1-Spring initializr/images/Initializr.png">
                            <a href="https://start.spring.io/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Spring initializr</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/044-脚手架/2-Aliyun Java initializr/images/Initializr.png">
                            <a href="https://start.aliyun.com/bootstrap.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>Aliyun Java initializr</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/04-环境/044-脚手架/3-IDEA Plugin Project/images/Initializr.png">
                            <a href="https://github.com/fuzhengwei/guide-idea-plugin-scaffolding" target="_blank" style="color:black;text-decoration:none;">
                                <span>IDEA Plugin</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        开发
                    </div>
                    <div class="category"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        框架
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/051-框架/1-Spring/images/Spring.png">
                            <a href="https://spring.io/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Spring</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/051-框架/2-SpringBoot/images/SpringBoot.png">
                            <a href="https://spring.io/projects/spring-boot" target="_blank" style="color:black;text-decoration:none;">
                                <span>SpringBoot</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/051-框架/3-MyBatis/images/MyBatis.png">
                            <a href="https://mybatis.org/mybatis-3/zh/index.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>MyBatis</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        RPC
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/052-RPC/1-Dubbo/images/Dubbo.png">
                            <a href="https://dubbo.apache.org/zh/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Dubbo</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/052-RPC/2-GRPC/images/GRPC.png">
                            <a href="https://grpc.io/" target="_blank" style="color:black;text-decoration:none;">
                                <span>GRPC</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        网关
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/058-网关/1-Spring Cloud Gateway/images/Spring.png">
                            <a href="https://spring.io/projects/spring-cloud-gateway" target="_blank" style="color:black;text-decoration:none;">
                                <span>Spring Cloud Gateway</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/058-网关/2-Soul/images/Soul.png">
                            <a href="#" target="_blank" style="color:black;text-decoration:none;">
                                <span>Soul</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/058-网关/3-Zuul/images/Zuul.png">
                            <a href="https://github.com/Netflix/zuul" target="_blank" style="color:black;text-decoration:none;">
                                <span>Zuul</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/058-网关/4-OpenResty/images/OpenResty.png">
                            <a href="https://openresty.org/cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>OpenResty</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        分库分表
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/057-分库分表/1-MyCat/images/MyCat.png">
                            <a href="http://www.mycat.org.cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>MyCat</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/057-分库分表/2-Sharding-Jdbc/images/Sharding-Jdbc.png">
                            <a href="https://shardingsphere.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Sharding-Jdbc</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        数据源
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/056-数据源/1-DBCP/images/DBCP.png">
                            <a href="https://commons.apache.org/proper/commons-dbcp/" target="_blank" style="color:black;text-decoration:none;">
                                <span>DBCP</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/056-数据源/2-C3P0/images/C3P0.png">
                            <a href="https://www.mchange.com/projects/c3p0/" target="_blank" style="color:black;text-decoration:none;">
                                <span>C3P0</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/056-数据源/3-Druid/images/Druid.png">
                            <a href="https://github.com/alibaba/druid" target="_blank" style="color:black;text-decoration:none;">
                                <span>Druid</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        注册中心
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/055-注册中心/1-ZooKeeper/images/Zookeeper.png">
                            <a href="https://zookeeper.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>ZooKeeper</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/055-注册中心/2-Eureka/images/Eureka.png">
                            <a href="https://github.com/Netflix/eureka" target="_blank" style="color:black;text-decoration:none;">
                                <span>Eureka</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/055-注册中心/3-Nacos/images/Nacos.png">
                            <a href="https://nacos.io/zh-cn/docs/what-is-nacos.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>Nacos</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        任务调度
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/054-任务调度/1-xxl-job/images/xxl-job.png">
                            <a href="https://www.xuxueli.com/xxl-job/" target="_blank" style="color:black;text-decoration:none;">
                                <span>xxl-Job</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/054-任务调度/2-elastic-job/images/elastic-job.png">
                            <a href="https://shardingsphere.apache.org/elasticjob/current/cn/overview/" target="_blank" style="color:black;text-decoration:none;">
                                <span>elastic-job</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/054-任务调度/3-quartz/images/quartz.png">
                            <a href="http://www.quartz-scheduler.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>quartz</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        MQ
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/053-MQ/1-Kafka/images/Kafka.png">
                            <a href="https://kafka.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Kafka</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/053-MQ/2-RabbitMQ/images/RabbitMQ.png">
                            <a href="https://www.rabbitmq.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>RabbitMQ</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/053-MQ/3-RocketMQ/images/RocketMQ.png">
                            <a href="https://rocketmq.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>RocketMQ</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/053-MQ/4-ActiveMQ/images/ActiveMQ.png">
                            <a href="https://activemq.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>ActiveMQ</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        关系型数据库
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/059-关系型数据库/1-MySql/images/MySql.png">
                            <a href="https://www.mysql.com/cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>MySql</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/059-关系型数据库/2-Oracle/images/Oracle.png">
                            <a href="https://www.oracle.com/hk/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Oracle</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/059-关系型数据库/3-TiDB/images/TIDB.png">
                            <a href="https://docs.pingcap.com/zh/tidb/stable" target="_blank" style="color:black;text-decoration:none;">
                                <span>TiDB</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        NoSQL 数据库
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0510-NoSQL数据库/1-Redis/images/Redis.png">
                            <a href="https://redis.io/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Redis</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0510-NoSQL数据库/2-Memcached/images/Memcached.png">
                            <a href="https://memcached.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Memcached</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        图形数据库
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0511-图形数据库/1-Neo4j/images/Neo4j.png">
                            <a href="https://neo4j.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Neo4j</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        文件存储
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0512-文件存储/1-Hbase/images/Hbase.png">
                            <a href="https://hbase.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Hbase</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0512-文件存储/2-Hadoop/images/Hadoop.png">
                            <a href="#" target="_blank" style="color:black;text-decoration:none;">
                                <span>Hadoop</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0512-文件存储/3-MongoDB/images/MongoDB.png">
                            <a href="https://www.mongodb.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>MongoDB</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        搜索引擎
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0513-搜索引擎/1-ElasticSearch/images/Elasticsearch.png">
                            <a href="https://www.elastic.co/cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>ElasticSearch</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0513-搜索引擎/2-Solr/images/Solr.png">
                            <a href="https://solr.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Solr</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        大数据
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0514-大数据/1-Flink/images/Flink.png">
                            <a href="https://flink.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Flink</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0514-大数据/2-Storm/images/Storm.png">
                            <a href="https://storm.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Storm</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0514-大数据/3-Spark/images/Spark.png">
                            <a href="https://spark.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Spark</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0514-大数据/4-Hive/images/Hive.png">
                            <a href="https://hive.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Hive</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        工作流
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/065-工作流/1-BPMN/images/BPMN.png">
                            <a href="https://www.bpmn.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>BPMN</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        网络编程
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/064-网络编程/1-Netty/images/Netty.png">
                            <a href="https://netty.io/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Netty</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/064-网络编程/2-Mina/images/Mina.png">
                            <a href="https://mina.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Mina</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/064-网络编程/3-WebFlux/images/WebFlux.png">
                            <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>WebFlux</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        字节码框架
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/063-字节码框架/1-ASM/images/ASM.png">
                            <a href="https://bugstack.cn/md/bytecode/asm-document/1%E5%BC%95%E8%A8%80.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>ASM</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/063-字节码框架/2-Javassist/images/Javassist.png">
                            <a href="https://www.javassist.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Javassist</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/063-字节码框架/3-Byte-Buddy/images/Byte-Buddy.png">
                            <a href="https://bytebuddy.net/#/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Byte-Buddy</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        认证授权
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/062-认证授权/1-shiro/images/shiro.png">
                            <a href="https://shiro.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>shiro</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/062-认证授权/2-jwt/images/jwt.png">
                            <a href="https://jwt.io/" target="_blank" style="color:black;text-decoration:none;">
                                <span>jwt</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        类库
                    </div>
                    <div class="category"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        序列化
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/061-序列化/1-fastjson/images/fastjson.png">
                            <a href="https://github.com/alibaba/fastjson" target="_blank" style="color:black;text-decoration:none;">
                                <span>fastjson</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/061-序列化/2-jackson/images/jackson.png">
                            <a href="https://github.com/FasterXML/jackson" target="_blank" style="color:black;text-decoration:none;">
                                <span>jackson</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/061-序列化/3-Gson/images/Gson.png">
                            <a href="https://github.com/google/gson" target="_blank" style="color:black;text-decoration:none;">
                                <span>Gson</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33ffcc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #00cccc;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        数据同步
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0515-数据同步/1-Otter/Otter.png">
                            <a href="https://github.com/alibaba/otter" target="_blank" style="color:black;text-decoration:none;">
                                <span>Otter</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/05-开发/0515-数据同步/2-Canal/Canal.png">
                            <a href="https://github.com/alibaba/canal" target="_blank" style="color:black;text-decoration:none;">
                                <span>Canal</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        日志
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/066-日志/1-log4j/images/log4j.png">
                            <a href="https://logging.apache.org/log4j/2.x/" target="_blank" style="color:black;text-decoration:none;">
                                <span>log4j</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/066-日志/2-logback/images/logback.png">
                            <a href="https://logback.qos.ch/" target="_blank" style="color:black;text-decoration:none;">
                                <span>logback</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/066-日志/3-slf4j/images/slf4j.png">
                            <a href="https://www.slf4j.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>slf4j</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        服务治理
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/067-服务治理/1-Hystrix/images/Hystrix.png">
                            <a href="https://github.com/Netflix/Hystrix" target="_blank" style="color:black;text-decoration:none;">
                                <span>Hystrix</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/067-服务治理/2-Sentinel/images/Sentinel.png">
                            <a href="https://sentinelguard.io/zh-cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Sentinel</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/067-服务治理/3-RateLimiter/images/RateLimiter.png">
                            <a href="https://guava.dev/releases/19.0/api/docs/index.html?com/google/common/util/concurrent/RateLimiter.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>RateLimiter</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        HTTP框架
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/068-HTTP框架/1-HttpClient/images/HttpClient.png">
                            <a href="https://hc.apache.org/httpcomponents-client-5.1.x/" target="_blank" style="color:black;text-decoration:none;">
                                <span>HttpClient</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/068-HTTP框架/2-RestTemplate/images/RestTemplate.png">
                            <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>RestTemplate</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        规则引擎
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/069-规则引擎/1-Drools/images/Drools.png">
                            <a href="https://www.drools.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Drools</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/069-规则引擎/2-Activiti7/images/Activiti.png">
                            <a href="https://activiti.gitbook.io/activiti-7-developers-guide/getting-started/getting-started-activiti-core" target="_blank" style="color:black;text-decoration:none;">
                                <span>Activiti7</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        Redis
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/0610-Redis/1-Redisson/images/Redisson.png">
                            <a href="https://github.com/redisson/redisson" target="_blank" style="color:black;text-decoration:none;">
                                <span>Redisson</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/0610-Redis/2-RedisTemplate/images/RedisTemplate.png">
                            <a href="https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>RedisTemplate</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/0610-Redis/3-Jedis/images/Jedis.png">
                            <a href="https://github.com/redis/jedis" target="_blank" style="color:black;text-decoration:none;">
                                <span>Jedis</span>
                            </a>
                        </div>
                    </div>
                </div>
                <di class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #DA70D6;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFBBFF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        其他
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/0611-其他/1-Guava/images/Guava.png">
                            <a href="https://github.com/google/guava" target="_blank" style="color:black;text-decoration:none;">
                                <span>Guava</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/0611-其他/2-Hutool/images/Hutool.png">
                            <a href="https://hutool.cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Hutool</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/06-类库/0611-其他/3-TTL/images/TTL.png">
                            <a href="https://github.com/alibaba/transmittable-thread-local" target="_blank" style="color:black;text-decoration:none;">
                                <span>TTL</span>
                            </a>
                        </div>
                    </div>
                </di>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFF68F;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        质量分析
                    </div>
                    <div class="category"
                         style="background-color: #FFD700;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        代码审查
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/091-代码审查/1-github/images/github.png">
                            <a href="https://github.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>github</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #C6E2FF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #A4D3EE;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        性能测试
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/08-测试/082-性能测试/1-Jemeter/images/Jemeter.png">
                            <a href="https://jmeter.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Jemeter</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #C6E2FF;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        测试
                    </div>
                    <div class="category"
                         style="background-color: #A4D3EE;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        单元测试
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/08-测试/081-单元测试/1-JUnit/images/JUnit.png">
                            <a href="https://junit.org/junit5/" target="_blank" style="color:black;text-decoration:none;">
                                <span>JUnit</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/08-测试/081-单元测试/2-TestNG/images/TestNG.png">
                            <a href="https://testng.org/doc/" target="_blank" style="color:black;text-decoration:none;">
                                <span>TestNG</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFAEB9;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FF3E96;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        API管理
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/07-调试/073-API管理/1-OpenApi/OpenApi.png">
                            <a href="https://www.openapis.org" target="_blank" style="color:black;text-decoration:none;">
                                <span>OpenApi</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/07-调试/073-API管理/2-Swagger/Swagger.png">
                            <a href="https://swagger.io/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Swagger</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/07-调试/073-API管理/3-Javadoc/Javadoc.png">
                            <a href="https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html" target="_blank" style="color:black;text-decoration:none;">
                                <span>Javadoc</span>
                            </a>
                        </div>
                    </div>

                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFAEB9;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FF3E96;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        调试工具
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/07-调试/072-调试工具/1-Postman/images/Postman.png">
                            <a href="https://www.postman.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Postman</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/07-调试/072-调试工具/2-ApiFox/images/ApiFox.png">
                            <a href="https://www.apifox.cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>ApiFox</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/07-调试/072-调试工具/3-ApiPost/images/ApiPost.png">
                            <a href="https://www.apipost.cn/" target="_blank" style="color:black;text-decoration:none;">
                                <span>ApiPost</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFAEB9;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        调试工具
                    </div>
                    <div class="category"
                         style="background-color: #FF3E96;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        Mock
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/07-调试/071-Mock/1-Mockito/images/Mock.png">
                            <a href="https://github.com/mockito/mockito" target="_blank" style="color:black;text-decoration:none;">
                                <span>Mockito</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFD700;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFF68F;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        代码规范
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/092-代码规范/1-p3c/images/p3c.png">
                            <a href="https://github.com/alibaba/p3c" target="_blank" style="color:black;text-decoration:none;">
                                <span>p3c</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/092-代码规范/1-pmd-idea/images/pmd-idea.png">
                            <a href="https://github.com/ybroeker/pmd-idea" target="_blank" style="color:black;text-decoration:none;">
                                <span>pmd-idea</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFD700;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFF68F;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        质量检测
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/093-质量检测/1-SonarQube/images/SonarQube.png">
                            <a href="https://www.sonarqube.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>SonarQube</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #FFD700;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #FFF68F;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        监控系统
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/094-监控系统/1-Dapper/images/Dapper.png">
                            <a href="https://bigbully.github.io/Dapper-translation/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Dapper</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/094-监控系统/2-Pinpoint/images/Pinpoint.png">
                            <a href="https://github.com/pinpoint-apm/pinpoint" target="_blank" style="color:black;text-decoration:none;">
                                <span>Pinpoint</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/094-监控系统/3-Prometheus/images/Prometheus.png">
                            <a href="https://github.com/prometheus/prometheus" target="_blank" style="color:black;text-decoration:none;">
                                <span>Prometheus</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/094-监控系统/4-SkyWalking/images/SkyWalking.png">
                            <a href="https://skywalking.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>SkyWalking</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/09-质量分析/094-监控系统/5-BeeAPM/images/BeeAPM.png">
                            <a href="https://github.com/hao117/bee-apm" target="_blank" style="color:black;text-decoration:none;">
                                <span>BeeAPM</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        发布部署
                    </div>
                    <div class="category"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        CI/CD
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/101-CICD/1-jenkins/images/jenkins.png">
                            <a href="https://www.jenkins.io/" target="_blank" style="color:black;text-decoration:none;">
                                <span>jenkins</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        发布
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/102-发布/1-War/images/War.png">
                            <a href="https://stackoverflow.com/questions/5871053/difference-between-jar-and-war-in-java" target="_blank" style="color:black;text-decoration:none;">
                                <span>War</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/102-发布/2-Jar/images/Jar.png">
                            <a href="https://stackoverflow.com/questions/5871053/difference-between-jar-and-war-in-java" target="_blank" style="color:black;text-decoration:none;">
                                <span>Jar</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        服务器
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/103-服务器/1-Nginx/images/Nginx.png">
                            <a href="https://www.nginx.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Nginx</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/103-服务器/2-Apache/images/Apache.png">
                            <a href="https://www.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Apache</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/103-服务器/3-Tomcat/images/Tomcat.png">
                            <a href="https://tomcat.apache.org/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Tomcat</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div css="row" style="justify-content: flex-start;font-size: 12px;display: flex;">
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <a href="#" target="_blank" style="color:black;text-decoration:none;">
                                <span></span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <a href="#" target="_blank" style="color:black;text-decoration:none;">
                                <span></span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <a href="#" target="_blank" style="color:black;text-decoration:none;">
                                <span></span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        云原生
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/106-云原生/1-Istio/images/Istio.png">
                            <a href="https://istio.io/latest/zh/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Istio</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        服务化
                    </div>
                    <div class="production"
                         style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                        <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                        <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                             src="https://bugstack.cn/images/roadmap/10-发布部署/105-服务化/1-PaaS/images/PaaS.png">
                        <a href="https://www.intel.com/content/www/us/en/cloud-computing/as-a-service.html" target="_blank" style="color:black;text-decoration:none;">
                            <span>PaaS</span>
                        </a>
                    </div>
                    <div class="production"
                         style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                        <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                        <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                             src="https://bugstack.cn/images/roadmap/10-发布部署/105-服务化/2-SaaS/images/SaaS.png">
                        <a href="https://www.intel.com/content/www/us/en/cloud-computing/as-a-service.html" target="_blank" style="color:black;text-decoration:none;">
                            <span>SaaS</span>
                        </a>
                    </div>
                    <div class="production"
                         style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                        <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                        <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                             src="https://bugstack.cn/images/roadmap/10-发布部署/105-服务化/3-IaaS/images/IaaS.png">
                        <a href="https://www.intel.com/content/www/us/en/cloud-computing/as-a-service.html" target="_blank" style="color:black;text-decoration:none;">
                            <span>IaaS</span>
                        </a>
                    </div>
                </div>
                <div class="column" style="width:150px; min-height:164px;">
                    <div class="route"
                         style="background-color: #33cc99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                    </div>
                    <div class="category"
                         style="background-color: #66ff99;height:30px;line-height:30px;font-size:14px;font-weight:700px;padding-left:24px;">
                        容器化
                    </div>
                    <div class="productions" style="margin-bottom: 16px;">
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #e1251b; left: 0; transform: scale(.6);">️A</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/104-容器化/1-Docker/images/Docker.png">
                            <a href="https://www.docker.com/" target="_blank" style="color:black;text-decoration:none;">
                                <span>Docker</span>
                            </a>
                        </div>
                        <div class="production"
                             style="cursor: pointer;height:24px;display:flex;align-items:center;padding-left:24px;position:relative;margin-top:2px;">
                            <span style="color: #fff; display: flex; justify-content: center; align-items: center; position: absolute; width: 24px; height: 24px; padding-top: 1px; border-radius: 50%; background-color: #999999; left: 0; transform: scale(.6);">️B</span>
                            <img class="icon" style="width: 16px;height: 16px;margin-right: 6px;"
                                 src="https://bugstack.cn/images/roadmap/10-发布部署/104-容器化/2-K8S/images/K8S.png">
                            <a href="https://kubernetes.io/zh-cn/docs/home/" target="_blank" style="color:black;text-decoration:none;">
                                <span>K8S</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 地图右侧 -->
        <div class="road-map-right" style="position:absolute;width:160px;height:600px;margin-left:1060px;">
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="row_right" style="right: 1px;margin-top:-40px;">
                    <div class="right-corner" style="margin: 100px auto;
                border-top-right-radius:60px;
                border-bottom-right-radius:60px;
                width: 40px;height:120px;
                box-shadow: 0 0 0 30px #FFA07A, 0 0 0 60px #FF7F00;
                clip-path: polygon(1px -120px, 220px 0px, 160px 220px, 1px 220px);"></div>
                </div>
            </div>
            <!-- 空白占位 -->
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="column" style="width:150px; min-height:164px;margin-left:20px;">
                </div>
            </div>
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="row_right" style="right: 1px;margin-top:-140px;">
                    <div class="right-corner" style="margin: 100px auto;
                border-top-right-radius:60px;
                border-bottom-right-radius:60px;
                width: 40px;height:104px;
                box-shadow: 0 0 0 30px #33ffcc, 0 0 0 60px #00cccc;
                clip-path: polygon(1px -120px, 220px 0px, 160px 220px, 1px 300px);"></div>
                </div>
            </div>
            <!-- 空白占位 -->
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="column" style="width:150px; min-height:180px;margin-left:20px;">
                </div>
            </div>
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="row_right" style="right: 1px;margin-top:-140px;">
                    <div class="right-corner" style="margin: 100px auto;
                border-top-right-radius:60px;
                border-bottom-right-radius:60px;
                width: 40px;height:120px;
                box-shadow: 0 0 0 30px #33ffcc, 0 0 0 60px #00cccc;
                clip-path: polygon(1px -120px, 220px 0px, 160px 220px, 1px 300px);"></div>
                </div>
            </div>
            <!-- 空白占位 -->
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="column" style="width:150px; min-height:164px;margin-left:20px;">
                </div>
            </div>
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="row_right" style="right: 1px;margin-top:-140px;">
                    <div class="right-corner" style="margin: 100px auto;
                border-top-right-radius:60px;
                border-bottom-right-radius:60px;
                width: 40px;height:104px;
                box-shadow: 0 0 0 30px #FFAEB9, 0 0 0 60px #FF3E96;
                clip-path: polygon(1px -120px, 220px 0px, 160px 220px, 1px 300px);"></div>
                </div>
            </div>
            <!-- 空白占位 -->
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="column" style="width:150px; min-height:164px;margin-left:20px;">
                </div>
            </div>
            <div css="row" style="justify-content:flex-start;font-size: 12px;display: flex;margin-left:-1px;">
                <div class="row_right" style="right: 1px;margin-top:-140px;">
                    <div class="right-corner" style="margin: 100px auto;
                border-top-right-radius:60px;
                border-bottom-right-radius:60px;
                width: 40px;height:146px;
                box-shadow: 0 0 0 30px #33cc99, 0 0 0 60px #66ff99;
                clip-path: polygon(1px -120px, 220px 0px, 160px 220px, 1px 300px);"></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {};
</script>

<style>
    .production:hover{
        background-color: #EEEEEE;
        border-radius: 4px;
    }
</style>