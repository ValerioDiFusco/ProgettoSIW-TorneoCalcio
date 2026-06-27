import React from 'react';

interface LinkProps{
	to: string;
	children: React.ReactNode
}

function Link({to,children}: LinkProps){
	return (
		<>
		<a href={to}>{children}</a>
		</>
	)
}

export default Link
