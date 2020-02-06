import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDoor } from 'app/shared/model/door.model';

@Component({
    selector: 'jhi-door-detail',
    templateUrl: './door-detail.component.html'
})
export class DoorDetailComponent implements OnInit {
    door: IDoor;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ door }) => {
            this.door = door;
        });
    }

    previousState() {
        window.history.back();
    }
}
