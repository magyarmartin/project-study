import React, { Component } from 'react';
import { Button, Input } from 'reactstrap';
import CollapsableMenu from './CollapsableMenu.jsx';
import './../css/CourseDetail.css';

class CourseDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      courseData: {
        name: 'Course name',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum',
        sections: [
        {
          id: 1,
          name: 'First section',
          lessons: [
            {id: 1, name: 'first lesson'}, {id: 2, name: 'second lesson'}
          ]
        }, {
          id: 2,
          name: 'Second section'
        }],
        rating: 3.5,
        comments: [
          {author: 'Joe', text: 'This is a short comment', date: '2018.01.02 12:54'},
          {author: 'Jim', text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor', date: '2018.01.02 12:56'}
        ]
      },
      

      collapse: true
    }
  }

  toggle() {
    if ( this.state.collapse ) {
      this.setState({collapse: false});
    } else {
      this.setState({collapse: true});
    }
  }

  render() {
    console.log(this.props.match.params.courseId)

    return (
      <div id="courseDetails" className="container">
        <div className="row">
          <div className="col-sm-8" id="courseDetails_course">
            <h1>{this.state.courseData.name}</h1>
            <img className="img-fluid" src="https://placebear.com/600/400" alt="Course"/>
            <div className="row extramargin">
              <div className="col-8">
                <h3>Description</h3>
              </div>
              <div className="col-4 d-flex justify-content-end">
                <Button color="warning">Subscribe</Button>
              </div>
            </div>
            <p>{this.state.courseData.description}</p>
            <h3>Sections</h3>
            <CollapsableMenu sections={this.state.courseData.sections}/>
          </div>
          <div className="col-sm-4" id="courseDetails_comments">
            <div className="row">
              <h3 className="col-sm-8">Rating</h3>
              <h3 className="col-sm-4 float-right">5/{this.state.courseData.rating}</h3>
            </div>
            <div className="row">
              <h3 className="col-sm-12">Comments:</h3>
            </div>
            {this.state.courseData.comments.map(comment => (
              <div>
                <hr/>
                <div className="row">
                  <h5 className="col-sm-6">{comment.author}</h5>
                  <span className="col-sm-6 float-right">{comment.date}</span>
                </div>
                <p>{comment.text}</p>
              </div>
            ))}
            <div >
              <Input type="textarea" name="text" id="new_comment" placeholder="Write an own comment"/>
              <div className="d-flex justify-content-end">
                <Button color="warning" className="extramargin">Send</Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default CourseDetail;
