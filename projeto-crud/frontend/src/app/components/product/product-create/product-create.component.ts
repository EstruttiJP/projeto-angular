import { ProductService } from '../product.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../product.model';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrl: './product-create.component.css'
})
export class ProductCreateComponent {

  product: Product = {
    name: '',
    price: 0,
    category: '',
    imageUrl: '',
    launchDate: new Date().toISOString().replace('Z', '')
  }

  selectedFile: File = null;

  constructor(private productService: ProductService, private router: Router) { }

  createProduct(): void {
    this.productService.uploadImage(this.selectedFile).subscribe(()=>{
      this.productService.create(this.product).subscribe(() => {
        this.productService.showMessage('Produto criado com sucesso! Aguarde a imagem ser carregada no sistema...');
        this.router.navigate(['/products-admin'])
      });
    })
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.product.imageUrl = "assets/" + this.selectedFile.name;
    }
  }

  cancel(): void {
    this.router.navigate(['/products-admin'])
  }
}
