import React, { useState, useCallback, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux'
import { showMessage } from "../MessageComponent/MessageSlice";
import io from "socket.io-client";
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';
import { CLIENT_RENEG_WINDOW } from 'tls';
import "./SongStreaming.css"

let socket;
const ENDPOINT = "http://10.20.24.36:9003";

export default function SampleComponent() {
    const [audiourl, setUrl] = useState("");
    const [chunks, setChunks] = useState([]);
    const [currentChunk, setCurrentChunk] = useState(null);
    const [maxChunkId, setMaxChunkId] = useState(0);
    const song_id = useSelector((state)=>(state.SongState.song_id) )
    
    useEffect(() => {
       
        // socket.on("connect_error", (err) => {
        //     console.log(`connect_error due to ${err.message}`);
        //   });
        socket = io(ENDPOINT, { transports: ['websocket'] });
        socket.on("connect", () => {
            console.log("connected");
        })
        socket.on("data_packet", (data) => {
            const baseData = "data:audio/mp3;base64,";
            const base64String =  baseData + btoa(String.fromCharCode(...new Uint8Array(data.packet_bytes)));
            
            try{
                chunks[data.no] = base64String;
            }
            catch{
                // console.log("sas")
                chunks.push(base64String)
            } 
            setMaxChunkId(data.no)
            if (data.no === 0) {
                // 
                setUrl(base64String)
                setCurrentChunk(0)
            }
            
        })
        // socket.disconnect();
    }, [ENDPOINT]);

    useEffect(() => {
        
        console.log(chunks)
        const datagram = {
            "song_id":song_id
        }
        socket.emit("get_packet",datagram)
      
        
    }, [song_id])
    
    
    
    

    const changeSongEnd = () => {
        console.log("now chunk",currentChunk + 1);
        if(currentChunk<=maxChunkId){
            setCurrentChunk(currentChunk + 1);
            setUrl(chunks[currentChunk])
        }
        
    }

    return (
        <div>
            {/* <ReactPlayer url={audiourl} /> */}
            {
                // audiourl !== "" &&
                <AudioPlayer
                    className="myaudio_player"
                    autoPlay
                    src={chunks[currentChunk]}
                    onPlay={e => console.log("onPlay")}
                    onEnded={e => changeSongEnd()}
                    showDownloadProgress={true}
                // other props here
                />
            }
        </div>
    );
};