import React from 'react';
import CourseCard from './CourseCard.jsx';
import './../css/CourseList.css';

const CourseList = () => {
	return (
		<div id="courseList" className="container">
			<div className="row">
				<div id="courseList_sorting" className="col-sm-4">
					<div className="form-group">
	            		<label htmlFor="input_pwd_conf">Sort by</label>
	            		<div className="custom-control custom-radio">
				        	<input type="radio" id="customRadio1" name="sorting" className="custom-control-input" value="teacher"/>
				        	<label className="custom-control-label" htmlFor="customRadio1">Name</label>
				      	</div>
				      	<div className="custom-control custom-radio">
				        	<input type="radio" id="customRadio2" name="sorting" className="custom-control-input" value="student"/>
				        	<label className="custom-control-label" htmlFor="customRadio2">Rating</label>
				      	</div>
				      	<div className="custom-control custom-radio">
				        	<input type="radio" id="customRadio3" name="sorting" className="custom-control-input" value="student"/>
				        	<label className="custom-control-label" htmlFor="customRadio3">Creation date</label>
				      	</div>
	          		</div>
	          		<div className="form-group">
	            		<label htmlFor="input_pwd_conf">Sorting direction</label>
	            		<div className="custom-control custom-radio">
				        	<input type="radio" id="customRadio4" name="order" className="custom-control-input" value="teacher"/>
				        	<label className="custom-control-label" htmlFor="customRadio4">Ascending</label>
				      	</div>
				      	<div className="custom-control custom-radio">
				        	<input type="radio" id="customRadio5" name="order" className="custom-control-input" value="student"/>
				        	<label className="custom-control-label" htmlFor="customRadio5">Descending</label>
				      	</div>
	          		</div>
				</div>
				<div id="courseList_courses" className="col-sm-8 ">
					<div className="col-sm-10 d-flex justify-content-between row">
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    				<CourseCard className="col-sm-2" courseName="The bears" rating="4.3" raters="232"/>
	    			</div>
				</div>
			</div>
			<nav aria-label="Page navigation example" id="courseList_courses_paginator" className="d-flex justify-content-center">
			 	<ul className="pagination">
					<li className="page-item"><a className="page-link" href="#">Previous</a></li>
					<li className="page-item"><a className="page-link" href="#">1</a></li>
					<li className="page-item"><a className="page-link" href="#">2</a></li>
					<li className="page-item"><a className="page-link" href="#">3</a></li>
					<li className="page-item"><a className="page-link" href="#">Next</a></li>
				</ul>
			</nav>
		</div>
	);
}

export default CourseList;