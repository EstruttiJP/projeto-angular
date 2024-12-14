import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Product, ProductImage, ProductResponse } from './product.model';
import { EMPTY, Observable, catchError, map } from 'rxjs';
import { HeaderService } from '../template/header/header.service';
import { User } from '../template/header/user.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private readonly baseUrlProduct = "http://localhost:8080/api/product/v2"
  // findProductByName/${name}?
  private readonly baseUrlFile = "http://localhost:8080/api/file/v2/uploadFile"
  private readonly baseUrlAuth = "http://localhost:8080/auth/signin"

  
  constructor(private snackBar: MatSnackBar, private http: HttpClient, private headerService: HeaderService) {}

  private createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': 'Bearer ' + this.headerService.getToken(),
      'Content-Type': 'application/json'
    });
  }

  showMessage(msg: string, isError: boolean = false): void {
    this.snackBar.open(msg, "X", {
      duration: 3000,
      horizontalPosition: "center",
      verticalPosition: "top",
      panelClass: isError ? ["msg-error"] : ["msg-success"],
    });
  }

  signin(user: User): Observable<User> {
    console.table(user)
    return this.http.post<User>(this.baseUrlAuth, user).pipe(
      map((response) => {
        this.headerService.setToken(response.accessToken, response.username);
      }),
      catchError((e) => this.errorHandler(e))
    );
  }
  
  read(page: number, size: number, direction: string): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(`${this.baseUrlProduct}?page=${page}&size=${size}&direction=${direction}`).pipe(
      map((response) => response),
      catchError((e) => this.errorHandler(e))
    );
  }
  
// `${this.baseUrlProduct}?page=${page}&size=${size}&direction=${direction}`

  readById(id: number): Observable<Product> {
    const url = `${this.baseUrlProduct}/${id}`;
    
    return this.http.get<Product>(url).pipe(
      map((response) => response),
      catchError((e) => this.errorHandler(e))
    );
    
  }

  readByName(name: string, page: number, size: number, direction: string): Observable<ProductResponse> {
    const url = `${this.baseUrlProduct}/findProductByName/${name}?page=${page}&size=${size}&direction=${direction}`;
    
    return this.http.get<Product>(url).pipe(
      map((response) => response),
      catchError((e) => this.errorHandler(e))
    );
    
  }

  delete(id: number): Observable<Product> {
    const headers = this.createHeaders();
    const url = `${this.baseUrlProduct}/${id}`;
    
    return this.http.delete<Product>(url, {headers}).pipe(
      map((response) => response),
      catchError((e) => this.errorHandler(e))
    );
    
  }
  
  create(product: Product): Observable<Product> {
    const headers = this.createHeaders();
    return this.http.post<Product>(this.baseUrlProduct, product, {headers}).pipe(
      map((obj) => obj),
      catchError((e) => this.errorHandler(e))
    );
  }
  
  update(product: Product): Observable<Product> {
    const headers = this.createHeaders();
    return this.http.put<Product>(this.baseUrlProduct, product, {headers}).pipe(
      map((obj) => obj),
      catchError((e) => this.errorHandler(e))
    );
  }

  uploadImage(image: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', image);

    // Configura o cabeçalho com o token
    const headers = new HttpHeaders({
        Authorization: "Bearer "+this.headerService.getToken(), // Exemplo: ajuste para obter o token
    });

    return this.http.post<any>(this.baseUrlFile, formData, { headers }).pipe(
        map((obj) => obj),
        catchError((e) => this.errorHandler(e))
    );
}

  errorHandler(e: any): Observable<any> {
    console.log(e)
    this.showMessage("Ocorreu um erro!: " + e.detail, true);
    return EMPTY;
  }
}
