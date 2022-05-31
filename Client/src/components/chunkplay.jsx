import React , { useEffect,useState } from 'react'
import io from "socket.io-client";
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';

let socket;
const ENDPOINT = "http://0.0.0.0:9005";

export default function Chunkplay() {
    const [audioSrc, setAudioSrc] = useState("")
    const [audioData, setAudioData] = useState([])
    const [ chunkSize , setChunkSize ] = useState(0); 
    // var audioSrc = "data:audio/mp3;base64, ";

    // String.prototype.replaceAt=function(index, char) {
    //     var a = this.split("");
    //     a[index] = char;
    //     return a.join("");
    // }
    useEffect(() => {
        const baseData = "data:audio/mp3;base64,";
        setAudioSrc(baseData+audioData.join(''))
        console.log("insiz=de")
    },[])


    useEffect(() => {
        socket = io(ENDPOINT, { transports: ['websocket'] });
        socket.on("connect_error", (err) => {
            console.log(`connect_error due to ${err.message}`);
          });
        
          socket.on("connect", () => {
            const datagram = {
                no:80000
            }
            socket.emit("get_packet",datagram)
        })
        
        socket.on("data_packet", (data) => {
            const baseData = "data:audio/mp3;base64,";
            const base64String =  btoa(String.fromCharCode(...new Uint8Array(data.packet_bytes)));
            // setAudioSrc(base64String)
            console.log(data.packet_bytes.byteLength)
            setAudioSrc(baseData+audioData.join(''))
            audioData.push(base64String)
            
            // if (data.no === 0) {
            //     setAudioSrc(baseData + base64String) 
            //     setChunkSize(base64String.length)
            // }        
            // else {
            //     // let newAudioSrc = audioSrc;
            //     // for(let i=0,ind=chunkSize*data.no;i<base64String.length;i++){
            //     //     var a = newAudioSrc.split("");
            //     //     // a[index] = char;
        
            //     //     // newAudioSrc.replaceAt(baseData.length+ind, base64String[i])
            //     //     a[baseData.length+ind] = base64String.charAt(i);
            //     //     newAudioSrc = a.join("")
            //     //     ind++;
            //     // }
            //     // setAudioSrc(newAudioSrc);
            //     console.log(audioSrc)
            //     // setAudioSrc(`${audioSrc}${base64String}`)
            // }
        })
    }, [ENDPOINT]);

    
    return (
        <div>
            song will be played            
            <audio src={audioSrc} controls  autoPlay  onEnded={(e)=>console.log("ended")} />
        </div>
    )
}
