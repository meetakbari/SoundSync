import React from "react";
import { useState, useEffect } from "react";
import "./SearchSong.css";
import axios from "axios";
import { useDispatch , useSelector} from "react-redux";
import {changeSong} from "./SongSlice";
import ReactLoading from 'react-loading';
import { showMessage } from "../../components/MessageComponent/MessageSlice";


export default function SearchSong() {
  const [searchString, setSearchString] = useState("");
  const [songList, setSongList] = useState([]);
  const [loading, setLoading] = useState(false);
  const song_id = useSelector((state)=>(state.SongState.song_id) )
  const  dispatch = useDispatch();

  useEffect(() => {
    setLoading(true)
    let mysearch = searchString;
    if (searchString === "") {
      console.log("inside");
      mysearch = "null";
    }
    var config = {
      method: "get",
      url: "http://10.20.24.36:8000/api/v1/search?search_query=" + mysearch,
    };

    axios(config)
      .then(function (response) {
        const songs = response.data.Response;
        setSongList(songs);
        setLoading(false)
        
      })
      .catch(function (error) {
        console.log(error);
        setLoading(false)
        dispatch(showMessage({message_type:"error",message_content:"Some thing went wrang !! <br /> <b>Error:</b> "+ error}))
      });
  }, [searchString]);

  return (
    <div className="SearchSong">
     <br/>
     <br/>
     <br/>
      <form className="">
        <input
          className="form-control mr-sm-2"
          type="search"
          placeholder="Search Song Here"
          aria-label="Search"
          value={searchString}
          onChange={(e) => setSearchString(e.target.value)}
        />
      </form>
      {
         loading && <ReactLoading className="loading" type={"spin"} color={"blue"} height={100} width={100} />
      }

      <div className="song__list">
        {songList.map((song, index) => {
          return (
            <div className="song__data" onClick={(e)=> dispatch(changeSong(song.id))}>
              <div className="song__name">{song.song_name}</div>
              <div className="singer_list">
              {
                  song.Singers.map((singer,index)=>{
                    return(<span className="singer__name">{singer+" "} </span>)
                  })
              }
              </div>
              <span>
                {
                song.id ===song_id ? 
                  <ReactLoading className="playing__loader" type={"cylon"} color={"rgba(243,84,86,.8)"} height={100} width={100} />
                  :""}</span>
            </div>
          );
        })}
      </div>
    </div>
  );
}
