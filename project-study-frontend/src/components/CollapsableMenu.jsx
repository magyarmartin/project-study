import React, {Component} from 'react';
import { Button, Card, CardBody, Collapse } from 'reactstrap';
import './../css/CollapsableMenu.css';

class CollapsableMenu extends Component {
	constructor(props) {
		super(props);
		let openCollapse = this.props.sections.map(section => false);
		this.state = {
			openCollapse: openCollapse
		};
	}

	onToggle(sectionNum) {
		let openCollapse = this.state.openCollapse;
		if ( openCollapse[sectionNum] ) {
			openCollapse[sectionNum]  = false;
		} else {
			openCollapse[sectionNum]  = true;
		}
		this.setState({openCollapse: openCollapse});
	}

	render() {
		let sectionNum = 0;
		return (
			<div>
				{this.props.sections.map(section => {
					let localSectionNum = sectionNum++;
	              	return (
	                	<div key={section.id}>
	                  		<Card color="warning" className={`collapsable ${this.state.openCollapse[localSectionNum] ? 'section_open' : ''}`} onClick={() => this.onToggle(localSectionNum)}>
	                  			<CardBody>{section.name}</CardBody>
	                  		</Card>
	                  		<Collapse isOpen={this.state.openCollapse[localSectionNum]}>
	                    		{section.lessons === undefined ? '' : section.lessons.map(lesson => {
	                      			return (
	                        			<Card key={lesson.id} className="lesson collapsable">
	                          				<CardBody>{lesson.name}</CardBody>
	                        			</Card>
	                    			)
	                    		})}
	                  		</Collapse>
	                	</div>
	              	)
	            })}
			</div>
		);
	}
}

export default CollapsableMenu;