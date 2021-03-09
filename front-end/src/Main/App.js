import { React } from 'react';

import 'bootswatch/dist/flatly/bootstrap.css'
import '../custom.css'
import Rotas from './routes';
import 'toastr/build/toastr.min.js'

import 'toastr/build/toastr.css'

import NavBar from '../Components/navbar';


function App() {
  return (
    <> 
      <NavBar />
      <Rotas />
    </>
  );
}


export default App;
