import React, { Component } from 'react'

export default class Navbar extends Component {
    render() {
        return (
            <div className="mynavbar">
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style={{ zIndex:100 }}>
                    <div class="container-fluid">
                        <a class="navbar-brand" href="/">SoundSync</a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Home</a>
                            </li>
                            <li class="nav-item">
                            <a class="nav-link" href="/upload">Upload Song</a>
                            </li>
                        </ul>
                        </div>
                    </div>
                </nav>
            </div>
        )
    }
}
