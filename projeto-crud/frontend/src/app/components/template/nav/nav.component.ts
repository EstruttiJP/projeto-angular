import { Location } from '@angular/common';
import { HeaderService } from './../header/header.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit {
  isAuth = false;
  constructor(private headerService: HeaderService, private location: Location) { }
  ngOnInit(): void {
    this.isAuth = this.headerService.isAuthenticated();
  }
  cleanToken() {
    this.headerService.clearLocalStorage();
    this.forceReload();
  }
  forceReload() { // Isto recarrega a página completamente 
    this.location.go(this.location.path());
    window.location.reload();
  }
}
