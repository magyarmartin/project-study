import React from 'react';
import { SearchIcon } from 'react-octicons';
import { InputGroup, InputGroupAddon, InputGroupText, Input } from 'reactstrap';
import './../css/SearchBar.css';

const SearchBar = () => {
	return (
		<InputGroup>
			<Input type="text" placeholder="Course's name"/>
			<InputGroupAddon addonType="prepend">
				<InputGroupText className="roundedBorder">
					<SearchIcon/>
				</InputGroupText>
			</InputGroupAddon>
		</InputGroup>
	);
}

export default SearchBar;