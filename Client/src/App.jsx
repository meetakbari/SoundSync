import "./App.css";
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import UploadSong from "./components/uploadSong";
import Navbar from "./components/Navbar"
import Home from "./components/Home";
import store from './store'
import { Provider } from 'react-redux'
import MessagePopup from "./components/MessageComponent/MessagePopup";

function App() {
  return (
    <Provider store={store}>
    <Router>
      <div>
        <MessagePopup />
        <Navbar />
        <Routes>
          <Route path="/upload" element={<UploadSong />} />
          <Route path="/" element={<Home />} />
        </Routes>
      </div>
    </Router>
    </Provider>
  );
}

export default App;
