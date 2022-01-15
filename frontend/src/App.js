import { BrowserRouter as Router } from 'react-router-dom';
import { NavBar } from './components';

import useSnackBar from './hooks/useSnackBar';
import GlobalStyles from './GlobalStyles';
import PageRouter from './PageRouter';

const App = () => {
  const { isSnackBarOpen, SnackBar } = useSnackBar();

  return (
    <>
      <GlobalStyles />
      <PageRouter />
      {isSnackBarOpen && <SnackBar />}
    </>
  );
};

export default App;
