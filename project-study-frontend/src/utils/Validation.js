class Validation {

	static isLongerThan( input, minLength ) {
		return input.length > minLength;
	}

	static isShorterThan( input, maxLength ) {
		return input.length < maxLength;
	}

	static isLengthBetween( input, minLength, maxLength ) {
		return input.length < maxLength && input.length > minLength;
	}

	static isTheSame( input, otherInput ) {
		return input === otherInput;
	}

	static isEmpty( input ) {
		return input.length === 0;
	}

	static isValidEmail( email ) {
		var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return re.test(email);
	}

}

export default Validation;