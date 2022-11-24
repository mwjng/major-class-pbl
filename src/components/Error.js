import { useLocation } from 'react-router-dom';

const Error=()=>{ //에러페이지
    const locations = useLocation();
    return(
      <div>
        <h3>Page not found at {locations.pathname}</h3>
      </div>
    );
  }
  export default Error;