import Link from './Link';
import './Link.css';
import './Navbar.css'; 

interface NavbarProps {
  isLogged: boolean;
}

function Navbar({ isLogged }: NavbarProps) {
  return (
    <div className="nav">
      
      <div className="nav-logo-section">
        <strong>
          <Link to="/">⚽ Tornei Calcio</Link>
        </strong>
        
		{!isLogged && (
		    <span className="login-span">
		            <a href="/login" className="login-anchor">
		            Log in
		         </a>
		          </span>
		        )}
		        
		{isLogged && (
		   <span className="logout-span">
		         <form action="/logout" method="POST" className="logout-form">
		       <button type="submit" className="logout-button">
		              Log out
		       </button>
		         </form>
		     </span>
		        )}
      </div>
      
      <div>
        <Link to="/tornei">Tornei</Link>
        <Link to="/giocatori">Giocatori</Link>
        <Link to="/squadre">Squadre</Link>
        <Link to="/partite">Calendario</Link>
      </div>

    </div>
  );
}

export default Navbar;