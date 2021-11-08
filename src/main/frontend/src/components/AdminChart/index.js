import React, { useState } from 'react';
import { Line, Bar, Pie } from 'react-chartjs-2';

import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'


const AdminChart = (props) => {
    const [chartType, setChartType] = useState("floor");

    let data = {}
    let options = {}
    let chartMaxWidth = "1200px"
    let chartHandler = [];

    switch (chartType) {
        case "floor":
            data = {
                labels: ['1', '2', '3', '4', '5', '6'],
                datasets: [
                    {
                        label: 'Floor Items',
                        data: [12, 19, 3, 5, 2, 3],
                        fill: false,
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgba(255, 99, 132, 0.2)',
                    },
                ],
            };
            options = {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };
            chartHandler.push(<Line data={data} options={options} />)
            break;
        case "charge":
            data = {
                labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                datasets: [
                    {
                        label: '# of Stations',
                        data: [12, 19, 3, 5, 2, 3],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                        ],
                        borderWidth: 1,
                    },
                ],
            };
            options = {
                scales: {
                    yAxes: [
                        {
                            ticks: {
                                beginAtZero: true,
                            },
                        },
                    ],
                },
            };
            chartHandler.push(<Bar data={data} options={options} />)
            break;
        case "dirt":
            data = {
                labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                datasets: [
                    {
                        label: 'Floor Type',
                        data: [12, 19, 3, 3, 2, 6],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                        ],
                        borderWidth: 1,
                    },
                ],
            };
            options = {
                indexAxis: 'y',
                // Elements options apply to all of the options unless overridden in a dataset
                // In this case, we are setting the border of each horizontal bar to be 2px wide
                elements: {
                    bar: {
                        borderWidth: 2,
                    },
                },
                responsive: true,
                plugins: {
                    legend: {
                        position: 'right',
                    },
                    title: {
                        display: true,
                        text: 'Chart.js Horizontal Bar Chart',
                    },
                },
            };
            chartHandler.push(<Bar data={data} options={options} />)
            break;
        case "path":
            chartMaxWidth = "600px"
            data = {
                labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                datasets: [
                    {
                        label: 'Types of Obstacles',
                        data: [12, 19, 3, 5, 2, 3],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                        ],
                        borderWidth: 1,
                    },
                ],
            }
            chartHandler.push(<Pie data={data} />)
            break;
    }

    return (
    <>
        <div className='header'>
            <h3 className='title'>{props.title}</h3>
            <Row className='links my-4'>
                <Col className='d-flex justify-content-around'>
                    <Button className="w-50 btn-light mx-2" onClick={() => setChartType("floor")}>
                        Floor Plan
                    </Button>
                    <Button className="w-50 btn-light" onClick={() => setChartType("charge")}>
                        Recharging
                    </Button>
                    <Button className="w-50 btn-light mx-2" onClick={() => setChartType("path")}>
                        Path
                    </Button>
                    <Button className="w-50 btn-light" onClick={() => setChartType("dirt")}>
                        Dirt
                    </Button>
                </Col>
            </Row>
        </div>
        <Row>
            <Col xs={0} />
            <Col xs={12} style={{maxWidth:chartMaxWidth}}>
                {chartHandler}
            </Col>
            <Col xs={0} />
        </Row>
    </>
    )

}

export default AdminChart;