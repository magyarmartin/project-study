import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { GrabberIcon } from 'react-octicons';
import { bindActionCreators } from 'redux';
import { Button, ButtonGroup, ButtonDropdown, DropdownMenu, DropdownItem, DropdownToggle } from 'reactstrap';
import { connect } from 'react-redux';
import SearchBar from '../components/SearchBar.jsx';
import logout from '../actions/logout.js';
import './../css/Header.css';

class Header extends Component {
	constructor(props) {
		super(props);

		this.state = {
			dropdownOpen: false,
		}
	}

	toggle() {
		this.setState({
			dropdownOpen: !this.state.dropdownOpen
		});
	}

	logout() {
		this.props.logout();
	}

	render() {
		return (
			<div id="header" className="container-fluid">
				<nav id="header__nav" className="row d-flex align-items-center">
					<div className="col-sm-3 d-flex justify-content-end">
						<Button color="warning" className="iconholder">
							<GrabberIcon id="header__nav__menuButton" />
						</Button>
					</div>
					<div className="col-sm-5">
						<SearchBar/>
					</div>

					{!this.props.user.authenticated ? 
					<ButtonGroup>
						<Link to="/signin" className="linkWithoutDecoration">
							<Button color="warning">
								Sign in
							</Button>
						</Link>
						<Link to="/login" className="linkWithoutDecoration">
							<Button color="warning">
								Login
							</Button>
						</Link>
					</ButtonGroup>
					: 
					<ButtonDropdown isOpen={this.state.dropdownOpen} toggle={() => this.toggle()}>
						<DropdownToggle caret color="warning">{this.props.user.firstName}</DropdownToggle>
						<DropdownMenu>
							<DropdownItem header>Header</DropdownItem>
							<Link to="/modifyDetails" className="linkWithoutDecoration"><DropdownItem>Change user details</DropdownItem></Link>
							<DropdownItem>Another Action</DropdownItem>
							<DropdownItem divider />
							<DropdownItem onClick={() => this.logout()}>Logout</DropdownItem>
						</DropdownMenu>
					</ButtonDropdown>}
				</nav>
			</div>
		);
	}
}

function mapStateToProps(state) {
	return {
	  user: state.user
	};
}

function mapDispatchToProps(dispatch) {
	return bindActionCreators({logout: logout}, dispatch);
  }

export default connect(mapStateToProps, mapDispatchToProps)(Header);