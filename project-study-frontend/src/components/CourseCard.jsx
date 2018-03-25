import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import { Card, CardImg, CardText, CardBody, CardTitle } from 'reactstrap';
import './../css/CourseCard.css';

class CourseCard extends Component {
	render () {
		return (
			<Link to={`/course/${this.props.courseId}`} className="linkWithoutDecoration">
				<Card className="courseCard">
			        <CardImg top width="100%" src="https://placebear.com/200/100" alt={this.props.courseName} />
			        <CardBody>
			          <CardTitle>{this.props.courseName}</CardTitle>
			          <CardText>Rating: 5/{this.props.rating} ({this.props.raters})</CardText>
			        </CardBody>
			    </Card>
			</Link>
		);
	}
}

export default CourseCard;