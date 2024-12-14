import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './views/home/home.component';
import { ProductCrudComponent } from './views/product-crud/product-crud.component';
import { ProductCreateComponent } from './components/product/product-create/product-create.component';
import { ProductUpdateComponent } from './components/product/product-update/product-update.component';
import { ProductDeleteComponent } from './components/product/product-delete/product-delete.component';
import { ProductClientComponent } from './views/product-client/product-client.component';

const routes: Routes = [ 
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "products",
    component: ProductClientComponent
  },
  {
    path: "products-admin",
    component: ProductCrudComponent
  },
  {
    path: "products-admin/create",
    component: ProductCreateComponent
  },
  {
    path: "products-admin/update/:id",
    component: ProductUpdateComponent
  },
  {
    path: "products-admin/delete/:id",
    component: ProductDeleteComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
