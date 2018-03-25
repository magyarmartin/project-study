import React from 'react';
import { Link } from 'react-router-dom';
import { GrabberIcon } from 'react-octicons';
import { Button, ButtonGroup } from 'reactstrap';
import SearchBar from './SearchBar.jsx';
import './../css/Header.css';

const Header = () => {
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

				<ButtonGroup>
					<Button color="warning">
						<Link to="/signin" className="linkWithoutDecoration">Sign in</Link>
					</Button>
					<Button color="warning">
						<Link to="/login" className="linkWithoutDecoration">Login</Link>
					</Button>
				</ButtonGroup>
			</nav>
		</div>
	);
}

export default Header;