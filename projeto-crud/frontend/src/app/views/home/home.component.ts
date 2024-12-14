import { Component, OnInit } from '@angular/core';
import { HeaderService } from '../../components/template/header/header.service';
import { User } from '../../components/template/header/user.model';
import { Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { ProductService } from '../../components/product/product.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{

  isAuth = false
  name = '';

  user: User = {
    username: '',
    password: ''
  }

  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required, Validators.minLength(4)]);
  hide = true;
  errorMessage = '';
  acess = false;

  constructor(private headerService: HeaderService, private productService: ProductService, private router: Router, private location: Location) {
    headerService.headerData = {
      title: "Início",
      icon: "home",
      routeUrl: ""
    }
  }

  ngOnInit(): void {
      if(this.isAuth = this.headerService.isAuthenticated()){
        this.name = this.headerService.getUsername();
      }
  }

  updateErrorMessage() {
    if (this.username.hasError('required')) {
      this.errorMessage = 'você deve inserir um username';
      this.acess = false
    } else {
      this.errorMessage = '';
      this.acess = true
    }
    if (this.password.hasError('required')) {
      this.errorMessage = 'você deve inserir uma senha';
      this.acess = false
    } else if (this.password.hasError('minlength')) {
      this.errorMessage = 'Deve haver no minimo 4 caracteres';
      this.acess = false
    } else {
      this.errorMessage = '';
      this.acess = true
    }
  }

  signin() {
    this.user.username = this.username.value;
    this.user.password = this.password.value;
    this.productService.signin(this.user).subscribe(() => {
      this.forceReload();
    })
  }

  forceReload() {
    this.location.go(this.location.path());
    window.location.reload();
  }


}
