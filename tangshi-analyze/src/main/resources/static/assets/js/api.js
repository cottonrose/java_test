function creationRanking(id) {
    $.get({
        url: "/analyze/creation/ranking",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            var myChart = echarts.init(document.getElementById(id));
            var options = {
                title: {
                    text: '唐诗创作排行榜'
                },
                tooltip: {},
                legend: {
                    data: ['数量(首)']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [{
                    name: '创作数量',
                    type: 'bar',
                    data: []
                }]
            };
            for (var key in  data) {
                options.xAxis.data.push(key);
                options.series[0].data.push(data[key]);
            }
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {

        }
    });
}

function cloudWorld(id) {
    $.get({
        url: "/analyze/cloud/words",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            var myChart = echarts.init(document.getElementById(id));
            var options = {
                series: [{
                    type: 'wordCloud',
                    shape: 'pentagon',
                    left: 'center',
                    top: 'center',
                    width: '80%',
                    height: '80%',
                    right: null,
                    bottom: null,
                    sizeRange: [12, 60],
                    rotationRange: [-90, 90],
                    rotationStep: 45,
                    gridSize: 8,
                    drawOutOfBound: false,
                    textStyle: {
                        normal: {
                            fontFamily: 'sans-serif',
                            fontWeight: 'bold',
                            color: function () {
                                return 'rgb(' + [
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160)
                                ].join(',') + ')';
                            }
                        },
                        emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    // Data is an array. Each array item must have name and value property.
                    data: []
                }]
            };
            for (var key in  data) {
                options.series[0].data.push({
                    name: key,
                    value: data[key],
                    textStyle: {
                        normal: {},
                        emphasis: {}
                    }
                });
            }
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {

        }
    });
}