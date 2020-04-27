import {Component, OnInit} from '@angular/core';
import {Item} from "../../shared/Item";
import {ItemListHttpService} from "../shared/http/item-list-http.service";

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.scss']
})
export class ItemListComponent implements OnInit {

  items: Item[];
  sampleImageUrl: string = 'https://lh3.googleusercontent.com/proxy/0uTqaUeqmcOLRh3btIIbW93YKuITINg3EryQM09hy-IdmAepJzJ9nIh5XN3Ha9LiHYEyBFFjtbgkmxUSmd1OpBffKBMe-fsKsiou8l8';

  constructor(
    private httpService: ItemListHttpService
  ) {
  }

  ngOnInit() {
    this.getItems()
  }

  getItems(){
    this.httpService.fetchItems(1).subscribe(result => this.items = result.items)
  }

  hasImage(item: Item): string {
    if (item.itemImageUrl == null){
      return this.sampleImageUrl
    }else {
      return item.itemImageUrl
    }
  }
}
