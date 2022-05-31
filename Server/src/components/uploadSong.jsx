import React, { Component } from "react";
import axios from "axios";
import '../css/uploadfile.css'


export default class uploadSong extends Component {
    constructor(props) {
        super(props);

        this.state = {
            song_name: "",
            album_name: "",
            composer: "",
            featuring: "",
            genre: "",
            lyricist: "",
            language: "",
            files: null
        };
    }

    async uploadfile() {
        // console.log(file);
        var data = new FormData();
        data.append("file", this.state.files[0]);
        data.append("song_name", this.state.song_name);
        data.append("album_name", this.state.album_name);
        data.append("composer", this.state.composer);
        data.append("featuring", this.state.featuring);
        data.append("genre", this.state.genre);
        data.append("lyricist", this.state.lyricist);
        data.append("original_release_date", new Date().toTimeString());
        data.append("language", this.state.language);

        // console.log("ahsdjfhklahjdsfhjkhj")
        var config = {
            method: "post",
            url: "http://0.0.0.0:8000/api/v1/upload_file",
            headers: { "Content-Type": "multipart/form-data" },
            data: data,
        };
        await axios(config)
            .then(function (response) {
                console.log(response.data);
            })
            .catch(function (error) {
                console.log(error);
            });
    }


    render() {
        return (
            <div className="UploadSong">
                <div className="pload_form_con">
                    <div className="mb-3">
                        <label htmlFor="song_name" className="form-label">Song Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="song_name"
                            aria-describedby="emailHelp"
                            value={this.state.song_name}
                            onChange={(e) => this.setState({ song_name: e.target.value })}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="album_name" className="form-label">Album Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="album_name"
                            aria-describedby="emailHelp"
                            value={this.state.album_name}
                            onChange={(e) => this.setState({ album_name: e.target.value })}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="composer" className="form-label">Composer Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="composer"
                            aria-describedby="emailHelp"
                            value={this.state.composer}
                            onChange={(e) => this.setState({ composer: e.target.value })}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="featuring" className="form-label">Featuring Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="featuring"
                            aria-describedby="emailHelp"
                            value={this.state.featuring}
                            onChange={(e) => this.setState({ featuring: e.target.value })}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="genre" className="form-label">Genre Name</label>
                        <input
                            type="text"
                            className="form-control"
                            id="genre"
                            aria-describedby="emailHelp"
                            value={this.state.genre}
                            onChange={(e) => this.setState({ genre: e.target.value })}
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="lyricist" className="form-label">Lyricist</label>
                        <input
                            type="text"
                            className="form-control"
                            id="lyricist"
                            aria-describedby="emailHelp"
                            value={this.state.lyricist}
                            onChange={(e) => this.setState({ lyricist: e.target.value })}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="language" className="form-label">Language</label>
                        <input
                            type="text"
                            className="form-control"
                            id="language"
                            aria-describedby="emailHelp"
                            value={this.state.language}
                            onChange={(e) => this.setState({ language: e.target.value })}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="file" className="form-label">Choose file</label>
                        <input
                            type="file"
                            className="form-control"
                            id="file"
                            accept=".mp3,.wav"
                            aria-describedby="emailHelp"
                            onChange={(e) => this.setState({ files: e.target.files })}
                        />
                    </div>

                    <button type="button" className="btn btn-primary" onClick={(e) => this.uploadfile()}>Submit</button>
                </div>
            </div>
        )

    }
}
