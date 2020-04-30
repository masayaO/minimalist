import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ItemListComponent} from './item-list/item-list.component';
import {ItemsRoutingModule} from "./items-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {ItemRegistrationComponent} from './item-registration/item-registration.component';
import {ReactiveFormsModule} from "@angular/forms";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";


@NgModule({
  imports: [
    CommonModule,
    ItemsRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FontAwesomeModule
  ],
  declarations: [ItemListComponent, ItemRegistrationComponent],
})
export class ItemsModule {
}
