import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ItemListComponent} from "./item-list/item-list.component";
import {ItemRegistrationComponent} from "./item-registration/item-registration.component";

const routes: Routes = [
  {path: '', component: ItemListComponent},
  {path: 'register', component: ItemRegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ItemsRoutingModule {
}
