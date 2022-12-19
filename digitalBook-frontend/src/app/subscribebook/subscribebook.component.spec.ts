import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscribebookComponent } from './subscribebook.component';

describe('SubscribebookComponent', () => {
  let component: SubscribebookComponent;
  let fixture: ComponentFixture<SubscribebookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubscribebookComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscribebookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
