import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HeaderData } from './header-data.model';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  private _headerData = new BehaviorSubject<HeaderData>({
    title: "Início",
    icon: "home",
    routeUrl: ''
  })

  get headerData(): HeaderData{
    return this._headerData.value
  }

  set headerData(headerData: HeaderData){
    this._headerData.next(headerData)
  }

  private readonly TOKEN_KEY = 'accessToken';
  private readonly USERNAME = 'username';

  constructor() {}

  setToken(token: string, username: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
    localStorage.setItem(this.USERNAME, username);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getUsername(): string | null {
    return localStorage.getItem(this.USERNAME);
  }

  clearLocalStorage(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USERNAME);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }


}
