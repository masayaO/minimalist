import { TestBed } from '@angular/core/testing';

import { ItemListHttpService } from './item-list-http.service';

describe('ItemListHttpService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ItemListHttpService = TestBed.get(ItemListHttpService);
    expect(service).toBeTruthy();
  });
});
