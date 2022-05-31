import React, { Component } from 'react'
import "./sidenavbar.css"

export default function SideNavBar(){
    return (
        <div className="SideNavbar">
            
            <ul className="sidenabar__button">
                <li className="active">Home</li>
                <li>Playlists</li>
                <li>Liked Songs</li>
            </ul>
        </div>
    )
    
}
