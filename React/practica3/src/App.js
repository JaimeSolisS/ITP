
import './App.css';


import PersonList from './components/PersonList';
import PersonInput from './components/PersonInput';
import PersonDelete from './components/PersonDelete';

function App() {
  return (
    <div className="App">
      <PersonInput/>
    <PersonList/>
    <PersonDelete/>
    </div>
  );
}

export default App;
