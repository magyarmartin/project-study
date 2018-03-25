import React from 'react';
import Carousel from './Carousel.jsx';
import CourseCard from './CourseCard.jsx';
import first from './../img/first.jpg';
import second from './../img/second.jpg';
import './../css/Main.css';

const Main = () => {
	const items = [
	  {
	    src: first
	  },
	  {
	    src: second,
	  }
	];
	return (
		<div id="mainContent">
			<Carousel items={items}/>
	    	<div id="mainContent__topCourses" className="container-fluid">
	    		<div className="row">
	    			<div className="col-sm-1"/>
	    			<div className="col-sm-11 d-flex p-3">
	    				<h1>Top rated courses</h1>
	    			</div>
	    		</div>
	    		<div className="row">
	    			<div className="col-sm-1"/>
	    			<div className="col-sm-10 d-flex justify-content-between row">
	    				<CourseCard className="col-sm-2" courseName="Build a Blockchain and a Cryptocurrency" rating="4.3" raters="232" courseId="1"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232" courseId="1"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232" courseId="1"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232" courseId="1"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232" courseId="1"/>
	    			</div>
	    			<div className="col-sm-1"/>
	    		</div>
	    	</div>
    	</div>
	);
}

export default Main;